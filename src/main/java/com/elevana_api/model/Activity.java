    package com.elevana_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Generated;

@Entity
@Table(
   name = "activities"
)
public class Activity {
   @Id
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "activity_id_generator"
   )
   @SequenceGenerator(
      name = "activity_id_generator",
      sequenceName = "activity_id_seq",
      allocationSize = 1
   )
   private Long id;
   @Column(
      nullable = false
   )
   private String title;
   @Column(
      nullable = false
   )
   private String type;
   @Column(
      nullable = false
   )
   private int xp;
   @Column(
      nullable = false
   )
   private String s3Url;
   private String mentorId;
   @ManyToOne(
      fetch = FetchType.LAZY
   )
   @JoinColumn(
      name = "classroom_id",
      nullable = true
   )
   @JsonBackReference
   private Classroom classroom;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public int getXp() {
      return this.xp;
   }

   @Generated
   public String getS3Url() {
      return this.s3Url;
   }

   @Generated
   public String getMentorId() {
      return this.mentorId;
   }

   @Generated
   public Classroom getClassroom() {
      return this.classroom;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setXp(final int xp) {
      this.xp = xp;
   }

   @Generated
   public void setS3Url(final String s3Url) {
      this.s3Url = s3Url;
   }

   @Generated
   public void setMentorId(final String mentorId) {
      this.mentorId = mentorId;
   }

   @Generated
   public void setClassroom(final Classroom classroom) {
      this.classroom = classroom;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Activity)) {
         return false;
      } else {
         Activity other = (Activity)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (this.getXp() != other.getXp()) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null) {
               if (other$id != null) {
                  return false;
               }
            } else if (!this$id.equals(other$id)) {
               return false;
            }

            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null) {
               if (other$title != null) {
                  return false;
               }
            } else if (!this$title.equals(other$title)) {
               return false;
            }

            label71: {
               Object this$type = this.getType();
               Object other$type = other.getType();
               if (this$type == null) {
                  if (other$type == null) {
                     break label71;
                  }
               } else if (this$type.equals(other$type)) {
                  break label71;
               }

               return false;
            }

            label64: {
               Object this$s3Url = this.getS3Url();
               Object other$s3Url = other.getS3Url();
               if (this$s3Url == null) {
                  if (other$s3Url == null) {
                     break label64;
                  }
               } else if (this$s3Url.equals(other$s3Url)) {
                  break label64;
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

            Object this$classroom = this.getClassroom();
            Object other$classroom = other.getClassroom();
            if (this$classroom == null) {
               if (other$classroom != null) {
                  return false;
               }
            } else if (!this$classroom.equals(other$classroom)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof Activity;
   }

   @Generated
   public int hashCode() {
       final int PRIME = 59;
       int result = 1;
       result = result * PRIME + this.getXp();
       Object $id = this.getId();
       result = result * PRIME + ($id == null ? 43 : $id.hashCode());
       Object $title = this.getTitle();
       result = result * PRIME + ($title == null ? 43 : $title.hashCode());
       Object $type = this.getType();
       result = result * PRIME + ($type == null ? 43 : $type.hashCode());
       Object $s3Url = this.getS3Url();
       result = result * PRIME + ($s3Url == null ? 43 : $s3Url.hashCode());
       Object $mentorId = this.getMentorId();
       result = result * PRIME + ($mentorId == null ? 43 : $mentorId.hashCode());
       Object $classroom = this.getClassroom();
       result = result * PRIME + ($classroom == null ? 43 : $classroom.hashCode());
       return result;
   }


   @Generated
   public String toString() {
      Long var10000 = this.getId();
      return "Activity(id=" + var10000 + ", title=" + this.getTitle() + ", type=" + this.getType() + ", xp=" + this.getXp() + ", s3Url=" + this.getS3Url() + ", mentorId=" + this.getMentorId() + ", classroom=" + String.valueOf(this.getClassroom()) + ")";
   }
}