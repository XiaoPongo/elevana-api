    package com.elevana_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "classrooms"
)
public class Classroom {
   @Id
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "classroom_id_generator"
   )
   @SequenceGenerator(
      name = "classroom_id_generator",
      sequenceName = "classroom_id_seq",
      allocationSize = 1
   )
   private Long id;
   private String name;
   private String description;
   private String classCode;
   private String mentorId;
   private boolean allowNewStudents = true;
   @OneToMany(
      mappedBy = "classroom",
      cascade = {CascadeType.ALL},
      orphanRemoval = true
   )
   @JsonManagedReference
   private List<Material> materials;
   @OneToMany(
      mappedBy = "classroom",
      cascade = {CascadeType.ALL},
      orphanRemoval = true
   )
   @JsonManagedReference
   private List<Activity> activities;
   @ManyToMany(
      mappedBy = "classrooms",
      fetch = FetchType.EAGER
   )
   @JsonManagedReference
   private Set<Student> students = new HashSet();

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public String getClassCode() {
      return this.classCode;
   }

   @Generated
   public String getMentorId() {
      return this.mentorId;
   }

   @Generated
   public boolean isAllowNewStudents() {
      return this.allowNewStudents;
   }

   @Generated
   public List<Material> getMaterials() {
      return this.materials;
   }

   @Generated
   public List<Activity> getActivities() {
      return this.activities;
   }

   @Generated
   public Set<Student> getStudents() {
      return this.students;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setClassCode(final String classCode) {
      this.classCode = classCode;
   }

   @Generated
   public void setMentorId(final String mentorId) {
      this.mentorId = mentorId;
   }

   @Generated
   public void setAllowNewStudents(final boolean allowNewStudents) {
      this.allowNewStudents = allowNewStudents;
   }

   @Generated
   public void setMaterials(final List<Material> materials) {
      this.materials = materials;
   }

   @Generated
   public void setActivities(final List<Activity> activities) {
      this.activities = activities;
   }

   @Generated
   public void setStudents(final Set<Student> students) {
      this.students = students;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Classroom)) {
         return false;
      } else {
         Classroom other = (Classroom)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (this.isAllowNewStudents() != other.isAllowNewStudents()) {
            return false;
         } else {
            label97: {
               Object this$id = this.getId();
               Object other$id = other.getId();
               if (this$id == null) {
                  if (other$id == null) {
                     break label97;
                  }
               } else if (this$id.equals(other$id)) {
                  break label97;
               }

               return false;
            }

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

            label76: {
               Object this$classCode = this.getClassCode();
               Object other$classCode = other.getClassCode();
               if (this$classCode == null) {
                  if (other$classCode == null) {
                     break label76;
                  }
               } else if (this$classCode.equals(other$classCode)) {
                  break label76;
               }

               return false;
            }

            Object this$mentorId = this.getMentorId();
            Object other$mentorId = other.getMentorId();
            if (this$mentorId == null) {
               if (other$mentorId != null) {
                  return false;
               }
            } else if (!this$mentorId.equals(other$mentorId)) {
               return false;
            }

            Object this$materials = this.getMaterials();
            Object other$materials = other.getMaterials();
            if (this$materials == null) {
               if (other$materials != null) {
                  return false;
               }
            } else if (!this$materials.equals(other$materials)) {
               return false;
            }

            Object this$activities = this.getActivities();
            Object other$activities = other.getActivities();
            if (this$activities == null) {
               if (other$activities != null) {
                  return false;
               }
            } else if (!this$activities.equals(other$activities)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof Classroom;
   }

   @Generated
   public int hashCode() {
       final int PRIME = 59;
       int result = 1;
       result = result * PRIME + (this.isAllowNewStudents() ? 79 : 97);
       Object $id = this.getId();
       result = result * PRIME + ($id == null ? 43 : $id.hashCode());
       Object $name = this.getName();
       result = result * PRIME + ($name == null ? 43 : $name.hashCode());
       Object $description = this.getDescription();
       result = result * PRIME + ($description == null ? 43 : $description.hashCode());
       Object $classCode = this.getClassCode();
       result = result * PRIME + ($classCode == null ? 43 : $classCode.hashCode());
       Object $mentorId = this.getMentorId();
       result = result * PRIME + ($mentorId == null ? 43 : $mentorId.hashCode());
       Object $materials = this.getMaterials();
       result = result * PRIME + ($materials == null ? 43 : $materials.hashCode());
       Object $activities = this.getActivities();
       result = result * PRIME + ($activities == null ? 43 : $activities.hashCode());
       return result;
   }


   @Generated
   public String toString() {
      Long var10000 = this.getId();
      return "Classroom(id=" + var10000 + ", name=" + this.getName() + ", description=" + this.getDescription() + ", classCode=" + this.getClassCode() + ", mentorId=" + this.getMentorId() + ", allowNewStudents=" + this.isAllowNewStudents() + ", materials=" + String.valueOf(this.getMaterials()) + ", activities=" + String.valueOf(this.getActivities()) + ")";
   }
}