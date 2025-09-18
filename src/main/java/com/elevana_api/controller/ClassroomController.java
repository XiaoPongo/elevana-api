    package com.elevana_api.controller;

import com.elevana_api.dto.CreateClassroomRequest;
import com.elevana_api.model.Classroom;
import com.elevana_api.service.ClassroomService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/classrooms"})
public class ClassroomController {
   private static final Logger logger = LoggerFactory.getLogger(ClassroomController.class);
   @Autowired
   private ClassroomService classroomService;

   @PostMapping
   public ResponseEntity<?> createClassroom(@RequestBody CreateClassroomRequest request, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      logger.info("POST /api/classrooms request received for mentorId: {}", mentorId);

      try {
         Classroom newClassroom = this.classroomService.createClassroom(request, mentorId);
         return ResponseEntity.ok(newClassroom);
      } catch (Exception var5) {
         logger.error("!!! ERROR creating classroom for mentorId: {}", mentorId, var5);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
      }
   }

   @GetMapping
   public ResponseEntity<List<Classroom>> getMyClassrooms(@AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      List<Classroom> classrooms = this.classroomService.getClassroomsForMentor(mentorId);
      return ResponseEntity.ok(classrooms);
   }

   @PutMapping({"/{classId}"})
   public ResponseEntity<Classroom> updateClassroom(@PathVariable Long classId, @RequestBody Classroom updatedDetails, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      logger.info("PUT /api/classrooms/{} request received for mentorId: {}", classId, mentorId);
      return (ResponseEntity)this.classroomService.updateClassroom(classId, mentorId, updatedDetails).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
   }

   @DeleteMapping({"/{classId}"})
   public ResponseEntity<Void> deleteClassroom(@PathVariable Long classId, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      logger.info("DELETE /api/classrooms/{} request received for mentorId: {}", classId, mentorId);
      boolean isDeleted = this.classroomService.deleteClassroom(classId, mentorId);
      return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
   }

   @DeleteMapping({"/{classId}/students/{studentId}"})
   public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long classId, @PathVariable String studentId, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      boolean isRemoved = this.classroomService.removeStudentFromClass(classId, studentId, mentorId);
      return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
   }
}