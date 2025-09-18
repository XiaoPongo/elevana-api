    package com.elevana_api.service;

import com.elevana_api.model.Classroom;
import com.elevana_api.model.Material;
import com.elevana_api.repository.ClassroomRepository;
import com.elevana_api.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MaterialService {
   @Autowired
   private MaterialRepository materialRepository;
   @Autowired
   private S3Service s3Service;
   @Autowired
   private ClassroomRepository classroomRepository;

   public List<Material> getMaterialsForMentor(String mentorId) {
      return this.materialRepository.findByMentorId(mentorId);
   }

   @Transactional
   public Material uploadMaterial(MultipartFile file, String mentorId, Long classroomId) throws IOException {
       String s3Url = this.s3Service.uploadFile(file, "materials");

       Material material = new Material();
       material.setDisplayName(file.getOriginalFilename());
       material.setFileType(file.getContentType());
       material.setS3Url(s3Url);
       material.setMentorId(mentorId);

       // 🔑 FIX: Set file size to avoid null constraint violation
       material.setFileSize(file.getSize());

       if (classroomId != null) {
           Classroom classroom = this.classroomRepository.findByIdAndMentorId(classroomId, mentorId)
                   .orElseThrow(() -> new EntityNotFoundException("Classroom not found or access denied"));
           material.setClassroom(classroom);
       }

       return this.materialRepository.save(material);
   }

   @Transactional
   public void deleteMaterials(List<Long> materialIds, String mentorId) {
      List<Material> materialsToDelete = this.materialRepository.findAllById(materialIds);
      Iterator var4 = materialsToDelete.iterator();

      Material material;
      do {
         if (!var4.hasNext()) {
            this.materialRepository.deleteAll(materialsToDelete);
            return;
         }

         material = (Material)var4.next();
      } while(material.getMentorId().equals(mentorId));

      throw new SecurityException("Access denied to delete one or more materials.");
   }

   @Transactional
   public void assignMaterialsToClassroom(List<Long> materialIds, Long classroomId, String mentorId) {
      List<Material> materialsToUpdate = this.materialRepository.findAllById(materialIds);
      Iterator var5 = materialsToUpdate.iterator();

      Material material;
      while(var5.hasNext()) {
         material = (Material)var5.next();
         if (!material.getMentorId().equals(mentorId)) {
            throw new SecurityException("Access denied to assign one or more materials.");
         }
      }

      if (classroomId != null) {
         Classroom classroom = (Classroom)this.classroomRepository.findByIdAndMentorId(classroomId, mentorId).orElseThrow(() -> {
            return new EntityNotFoundException("Classroom not found or access denied.");
         });
         Iterator var9 = materialsToUpdate.iterator();

         while (var9.hasNext()) {
             material = (Material) var9.next();  // reuse, no "Material" in front
             material.setClassroom(classroom);
         }
      } else {
    	  var5 = materialsToUpdate.iterator();

    	  while (var5.hasNext()) {
    	      material = (Material) var5.next();
    	      material.setClassroom(null);
    	  }
      }

      this.materialRepository.saveAll(materialsToUpdate);
   }

   public String generateDownloadUrl(Long materialId, String mentorId) {
      Material material = (Material)this.materialRepository.findById(materialId).orElseThrow(() -> {
         return new EntityNotFoundException("Material not found.");
      });
      if (!material.getMentorId().equals(mentorId)) {
         throw new SecurityException("Access denied to this material.");
      } else {
         try {
            URI uri = new URI(material.getS3Url());
            String objectKey = uri.getPath().substring(1);
            return this.s3Service.generatePresignedUrl(objectKey);
         } catch (URISyntaxException var6) {
            throw new RuntimeException("Invalid S3 URL format for material ID: " + materialId);
         }
      }
   }
}