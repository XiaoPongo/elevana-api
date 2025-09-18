    package com.elevana_api.controller;

import com.elevana_api.model.Activity;
import com.elevana_api.service.ActivityService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/api/upload"})
public class FileUploadController {
   @Autowired
   private ActivityService activityService;

   @PostMapping({"/{classroomId}/{type}"})
   public ResponseEntity<?> uploadActivity(@PathVariable Long classroomId, @PathVariable String type, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      if (!type.equals("mission") && !type.equals("case-study") && !type.equals("minigame")) {
         return ResponseEntity.badRequest().body("Invalid upload type.");
      } else {
         try {
            Activity newActivity = this.activityService.createActivityFromCsv(mentorId, classroomId, type, file);
            return ResponseEntity.ok(newActivity);
         } catch (IOException var7) {
            return ResponseEntity.internalServerError().body("Failed to process file.");
         } catch (IllegalArgumentException var8) {
            return ResponseEntity.status(404).body(var8.getMessage());
         }
      }
   }
}
 