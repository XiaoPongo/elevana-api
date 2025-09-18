    package com.elevana_api.dto;

import lombok.Generated;

public class CreateClassroomRequest {
   private String name;
   private String description;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CreateClassroomRequest)) {
         return false;
      } else {
         CreateClassroomRequest other = (CreateClassroomRequest)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null) {
               if (other$name != null) {
                  return false;
               }
            } else if (!this$name.equals(other$name)) {
               return false;
            }

            Object this$description = this.getDescription();
            Object other$description = other.getDescription();
            if (this$description == null) {
               if (other$description != null) {
                  return false;
               }
            } else if (!this$description.equals(other$description)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof CreateClassroomRequest;
   }

   @Generated
   public int hashCode() {
       final int PRIME = 59;
       int result = 1;
       Object $name = this.getName();
       result = result * PRIME + ($name == null ? 43 : $name.hashCode());
       Object $description = this.getDescription();
       result = result * PRIME + ($description == null ? 43 : $description.hashCode());
       return result;
   }


   @Generated
   public String toString() {
      String var10000 = this.getName();
      return "CreateClassroomRequest(name=" + var10000 + ", description=" + this.getDescription() + ")";
   }
}