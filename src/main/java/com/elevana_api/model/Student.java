    package com.elevana_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "students"
)
public class Student {
   @Id
   private String id;
   private String email;
   private String firstName;
   private String lastName;
   private int xp = 0;
   @ManyToMany(
      fetch = FetchType.LAZY
   )
   @JoinTable(
      name = "student_classrooms",
      joinColumns = {@JoinColumn(
   name = "student_id"
)},
      inverseJoinColumns = {@JoinColumn(
   name = "classroom_id"
)}
   )
   @JsonBackReference
   private Set<Classroom> classrooms = new HashSet();

   public void addClassroom(Classroom classroom) {
      this.classrooms.add(classroom);
      classroom.getStudents().add(this);
   }

   public void removeClassroom(Classroom classroom) {
      this.classrooms.remove(classroom);
      classroom.getStudents().remove(this);
   }

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public String getFirstName() {
      return this.firstName;
   }

   @Generated
   public String getLastName() {
      return this.lastName;
   }

   @Generated
   public int getXp() {
      return this.xp;
   }

   @Generated
   public Set<Classroom> getClassrooms() {
      return this.classrooms;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setFirstName(final String firstName) {
      this.firstName = firstName;
   }

   @Generated
   public void setLastName(final String lastName) {
      this.lastName = lastName;
   }

   @Generated
   public void setXp(final int xp) {
      this.xp = xp;
   }

   @Generated
   public void setClassrooms(final Set<Classroom> classrooms) {
      this.classrooms = classrooms;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Student)) {
         return false;
      } else {
         Student other = (Student)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (this.getXp() != other.getXp()) {
            return false;
         } else {
            label61: {
               Object this$id = this.getId();
               Object other$id = other.getId();
               if (this$id == null) {
                  if (other$id == null) {
                     break label61;
                  }
               } else if (this$id.equals(other$id)) {
                  break label61;
               }

               return false;
            }

            label54: {
               Object this$email = this.getEmail();
               Object other$email = other.getEmail();
               if (this$email == null) {
                  if (other$email == null) {
                     break label54;
                  }
               } else if (this$email.equals(other$email)) {
                  break label54;
               }

               return false;
            }

            Object this$firstName = this.getFirstName();
            Object other$firstName = other.getFirstName();
            if (this$firstName == null) {
               if (other$firstName != null) {
                  return false;
               }
            } else if (!this$firstName.equals(other$firstName)) {
               return false;
            }

            Object this$lastName = this.getLastName();
            Object other$lastName = other.getLastName();
            if (this$lastName == null) {
               if (other$lastName != null) {
                  return false;
               }
            } else if (!this$lastName.equals(other$lastName)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof Student;
   }

   @Generated
   public int hashCode() {
       final int PRIME = 59;
       int result = 1;
       result = result * PRIME + this.getXp();
       Object $id = this.getId();
       result = result * PRIME + ($id == null ? 43 : $id.hashCode());
       Object $email = this.getEmail();
       result = result * PRIME + ($email == null ? 43 : $email.hashCode());
       Object $firstName = this.getFirstName();
       result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
       Object $lastName = this.getLastName();
       result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
       return result;
   }


   @Generated
   public String toString() {
      String var10000 = this.getId();
      return "Student(id=" + var10000 + ", email=" + this.getEmail() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", xp=" + this.getXp() + ")";
   }
}
   