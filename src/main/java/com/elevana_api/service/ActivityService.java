    package com.elevana_api.service;

import com.elevana_api.model.Activity;
import com.elevana_api.model.Classroom;
import com.elevana_api.repository.ActivityRepository;
import com.elevana_api.repository.ClassroomRepository;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ActivityService {
   private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
   @Autowired
   private S3Service s3Service;
   @Autowired
   private ActivityRepository activityRepository;
   @Autowired
   private ClassroomRepository classroomRepository;

   public Activity createActivityFromCsv(String mentorId, Long classroomId, String type, MultipartFile file) throws IOException {
      logger.info("--> Verifying classroom access. Searching for classroomId: [{}] with mentorId: [{}]", classroomId, mentorId);
      Classroom classroom = (Classroom)this.classroomRepository.findByIdAndMentorId(classroomId, mentorId).orElseThrow(() -> {
         logger.error("--> !! FAILED LOOKUP !! Classroom with ID [{}] for mentor [{}] was not found.", classroomId, mentorId);
         return new IllegalArgumentException("Classroom not found or access denied.");
      });
      logger.info("--> Classroom verification successful for classroomId: [{}]. Proceeding with file upload.", classroomId);
      String fileUrl = this.s3Service.uploadFile(file, type);
      String title = "Untitled Activity";
      int xp = 0;
      BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

      String line;
      try {
         while((line = reader.readLine()) != null) {
            if (line.toLowerCase().startsWith("title,")) {
               title = line.split(",", 2)[1];
            }

            if (line.toLowerCase().startsWith("xp,")) {
               xp = Integer.parseInt(line.split(",", 2)[1]);
            }
         }
      } catch (Throwable var13) {
         try {
            reader.close();
         } catch (Throwable var12) {
            var13.addSuppressed(var12);
         }

         throw var13;
      }

      reader.close();
      Activity activity = new Activity();
      activity.setTitle(title);
      activity.setXp(xp);
      activity.setType(type);
      activity.setS3Url(fileUrl);
      activity.setMentorId(mentorId);
      activity.setClassroom(classroom);
      return (Activity)this.activityRepository.save(activity);
   }

   @Transactional
   public boolean unassignActivityFromClass(Long classId, Long activityId, String mentorId) {
      Optional<Classroom> classroomOpt = this.classroomRepository.findByIdAndMentorId(classId, mentorId);
      if (classroomOpt.isEmpty()) {
         logger.warn("Mentor {} tried to modify a classroom {} they don't own.", mentorId, classId);
         return false;
      } else {
         Optional<Activity> activityOpt = this.activityRepository.findById(activityId);
         if (activityOpt.isEmpty()) {
            logger.warn("Activity with ID {} not found.", activityId);
            return false;
         } else {
            Activity activity = (Activity)activityOpt.get();
            if (activity.getClassroom() != null && classId.equals(activity.getClassroom().getId())) {
               activity.setClassroom((Classroom)null);
               this.activityRepository.save(activity);
               logger.info("Successfully unassigned activity {} from classroom {}", activityId, classId);
               return true;
            } else {
               logger.warn("Activity {} was not assigned to classroom {}", activityId, classId);
               return false;
            }
         }
      }
   }
}