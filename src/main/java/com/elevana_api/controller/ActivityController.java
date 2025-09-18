    package com.elevana_api.controller;

import com.elevana_api.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class ActivityController {
   private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
   @Autowired
   private ActivityService activityService;

   @DeleteMapping({"/classrooms/{classId}/activities/{activityId}"})
   public ResponseEntity<Void> unassignActivityFromClass(@PathVariable Long classId, @PathVariable Long activityId, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      logger.info("DELETE /classrooms/{}/activities/{} request from mentor {}", new Object[]{classId, activityId, mentorId});
      boolean isUnassigned = this.activityService.unassignActivityFromClass(classId, activityId, mentorId);
      return isUnassigned ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
   }
}
