    package com.elevana_api.controller;

import com.elevana_api.dto.MaterialAssignDto;
import com.elevana_api.model.Material;
import com.elevana_api.service.MaterialService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping({"/api/materials"})
public class MaterialController {
   @Autowired
   private MaterialService materialService;

   @GetMapping
   public ResponseEntity<List<Material>> getMyMaterials(@AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      List<Material> materials = this.materialService.getMaterialsForMentor(mentorId);
      return ResponseEntity.ok(materials);
   }

   @PostMapping("/upload")
   public ResponseEntity<?> uploadMaterial(
           @RequestParam("file") MultipartFile file,
           @RequestParam("classroomId") Long classroomId,
           @AuthenticationPrincipal Jwt jwt) {

       try {
           String mentorId = jwt.getSubject(); // ✅ Use JWT, not request param

           if (file == null || file.isEmpty()) {
               return ResponseEntity.badRequest().body("File is required.");
           }
           if (classroomId == null) {
               return ResponseEntity.badRequest().body("classroomId is required.");
           }

           Material saved = materialService.uploadMaterial(file, mentorId, classroomId);
           return ResponseEntity.ok(saved);

       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("error", "Upload failed: " + e.getMessage()));
       }
   }


   @GetMapping("/{materialId}/download-url")
   public ResponseEntity<?> getMaterialDownloadUrl(
           @PathVariable Long materialId,
           @AuthenticationPrincipal Jwt jwt) {
       String mentorId = jwt.getSubject();
       try {
           String url = materialService.generateDownloadUrl(materialId, mentorId);
           return ResponseEntity.ok(Map.of("url", url));
       } catch (SecurityException ex) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Access Denied."));
       } catch (EntityNotFoundException ex) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Material not found."));
       } catch (Exception ex) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Unexpected error."));
       }
   }


   @PostMapping({"/assign"})
   public ResponseEntity<Void> assignMaterials(@RequestBody MaterialAssignDto dto, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      this.materialService.assignMaterialsToClassroom(dto.getMaterialIds(), dto.getClassroomId(), mentorId);
      return ResponseEntity.ok().build();
   }

   @DeleteMapping
   public ResponseEntity<Void> deleteMaterials(@RequestBody Map<String, List<Long>> payload, @AuthenticationPrincipal Jwt jwt) {
      String mentorId = jwt.getSubject();
      List<Long> materialIds = (List)payload.get("materialIds");
      if (materialIds != null && !materialIds.isEmpty()) {
         this.materialService.deleteMaterials(materialIds, mentorId);
         return ResponseEntity.noContent().build();
      } else {
         return ResponseEntity.badRequest().build();
      }
   }
}
 