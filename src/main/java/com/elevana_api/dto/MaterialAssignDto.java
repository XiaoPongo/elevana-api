    package com.elevana_api.dto;

import java.util.List;
import lombok.Generated;

public class MaterialAssignDto {
   private List<Long> materialIds;
   private Long classroomId;

   @Generated
   public List<Long> getMaterialIds() {
      return this.materialIds;
   }

   @Generated
   public Long getClassroomId() {
      return this.classroomId;
   }

   @Generated
   public void setMaterialIds(final List<Long> materialIds) {
      this.materialIds = materialIds;
   }

   @Generated
   public void setClassroomId(final Long classroomId) {
      this.classroomId = classroomId;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MaterialAssignDto)) {
         return false;
      } else {
         MaterialAssignDto other = (MaterialAssignDto)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$classroomId = this.getClassroomId();
            Object other$classroomId = other.getClassroomId();
            if (this$classroomId == null) {
               if (other$classroomId != null) {
                  return false;
               }
            } else if (!this$classroomId.equals(other$classroomId)) {
               return false;
            }

            Object this$materialIds = this.getMaterialIds();
            Object other$materialIds = other.getMaterialIds();
            if (this$materialIds == null) {
               if (other$materialIds != null) {
                  return false;
               }
            } else if (!this$materialIds.equals(other$materialIds)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MaterialAssignDto;
   }

   @Generated
   public int hashCode() {
       final int PRIME = 59;
       int result = 1;
       Object $classroomId = this.getClassroomId();
       result = result * PRIME + ($classroomId == null ? 43 : $classroomId.hashCode());
       Object $materialIds = this.getMaterialIds();
       result = result * PRIME + ($materialIds == null ? 43 : $materialIds.hashCode());
       return result;
   }


   @Generated
   public String toString() {
      String var10000 = String.valueOf(this.getMaterialIds());
      return "MaterialAssignDto(materialIds=" + var10000 + ", classroomId=" + this.getClassroomId() + ")";
   }
}
   