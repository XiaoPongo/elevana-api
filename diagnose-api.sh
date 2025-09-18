#!/bin/bash

# -----------------------
# CONFIG
# -----------------------
REGION="eu-north-1"
ENV_NAME="elevana-api-env-v1"
API_URL="https://${ENV_NAME}.eba-kqqge9q6.${REGION}.elasticbeanstalk.com/api/classrooms"

echo "🚀 Starting API Diagnostics for $ENV_NAME in $REGION"
echo "=================================================="

# 1. Ping domain
echo -e "\n🔍 Checking DNS resolution..."
ping -c 2 "${ENV_NAME}.eba-kqqge9q6.${REGION}.elasticbeanstalk.com"

# 2. External curl test (HTTP + HTTPS)
echo -e "\n🌍 External connectivity tests..."
curl -vk --max-time 10 "http://${ENV_NAME}.eba-kqqge9q6.${REGION}.elasticbeanstalk.com/"
curl -vk --max-time 10 "$API_URL"

# 3. Test open ports 80 & 443
echo -e "\n📡 Testing ports (80 & 443)..."
nc -zv "${ENV_NAME}.eba-kqqge9q6.${REGION}.elasticbeanstalk.com" 80
nc -zv "${ENV_NAME}.eba-kqqge9q6.${REGION}.elasticbeanstalk.com" 443

# 4. Fetch security groups for environment
echo -e "\n🛡 Checking security groups..."
ENV_ID=$(aws elasticbeanstalk describe-environments --environment-names "$ENV_NAME" --region "$REGION" \
  --query "Environments[0].EnvironmentId" --output text)

SGS=$(aws elasticbeanstalk describe-configuration-settings \
  --environment-id "$ENV_ID" --region "$REGION" \
  --query "ConfigurationSettings[0].OptionSettings[?OptionName=='SecurityGroups'].Value" --output text)

echo "Found Security Groups: $SGS"
for SG in $SGS; do
  echo "➡ Rules for SG $SG"
  aws ec2 describe-security-groups --group-ids $SG --region "$REGION" \
    --query "SecurityGroups[0].IpPermissions"
done

# 5. (Optional) SSH into EC2 and test internally
echo -e "\n⚡ Testing inside EC2..."
EB_INSTANCES=$(aws elasticbeanstalk describe-environment-resources --environment-name "$ENV_NAME" --region "$REGION" \
  --query "EnvironmentResources.Instances[*].Id" --output text)
echo "Instances: $EB_INSTANCES"

for INSTANCE in $EB_INSTANCES; do
  echo "➡ Checking $INSTANCE"
  PUBLIC_IP=$(aws ec2 describe-instances --instance-ids $INSTANCE --region "$REGION" \
    --query "Reservations[0].Instances[0].PublicIpAddress" --output text)
  echo "Instance IP: $PUBLIC_IP"
  echo "👉 Run manually: ssh ec2-user@$PUBLIC_IP"
done

echo -e "\n✅ Diagnostics complete!"