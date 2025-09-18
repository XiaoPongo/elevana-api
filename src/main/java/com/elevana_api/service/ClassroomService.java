    package com.elevana_api.service;

import com.elevana_api.dto.CreateClassroomRequest;
import com.elevana_api.model.Classroom;
import com.elevana_api.model.Student;
import com.elevana_api.repository.ClassroomRepository;
import com.elevana_api.repository.StudentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
   private static final Logger logger = LoggerFactory.getLogger(ClassroomService.class);
   @Autowired
   private ClassroomRepository classroomRepository;
   @Autowired
   private StudentRepository studentRepository;

   public Classroom createClassroom(CreateClassroomRequest request, String mentorId) {
      Classroom classroom = new Classroom();
      classroom.setName(request.getName());
      classroom.setDescription(request.getDescription());
      classroom.setMentorId(mentorId);
      classroom.setClassCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
      return (Classroom)this.classroomRepository.save(classroom);
   }

   public List<Classroom> getClassroomsForMentor(String mentorId) {
      return this.classroomRepository.findByMentorId(mentorId);
   }

   public Optional<Classroom> updateClassroom(Long classId, String mentorId, Classroom updatedDetails) {
      return this.classroomRepository.findByIdAndMentorId(classId, mentorId).map((existingClassroom) -> {
         existingClassroom.setName(updatedDetails.getName());
         existingClassroom.setDescription(updatedDetails.getDescription());
         existingClassroom.setAllowNewStudents(updatedDetails.isAllowNewStudents());
         return (Classroom)this.classroomRepository.save(existingClassroom);
      });
   }

   public boolean deleteClassroom(Long classId, String mentorId) {
      if (this.classroomRepository.existsByIdAndMentorId(classId, mentorId)) {
         this.classroomRepository.deleteById(classId);
         return true;
      } else {
         return false;
      }
   }

   @Transactional
   public boolean removeStudentFromClass(Long classId, String studentId, String mentorId) {
      Optional<Classroom> classroomOpt = this.classroomRepository.findByIdAndMentorId(classId, mentorId);
      if (classroomOpt.isEmpty()) {
         return false;
      } else {
         Optional<Student> studentOpt = this.studentRepository.findById(studentId);
         if (studentOpt.isEmpty()) {
            return false;
         } else {
            Classroom classroom = (Classroom)classroomOpt.get();
            Student student = (Student)studentOpt.get();
            if (classroom.getStudents().contains(student)) {
               classroom.getStudents().remove(student);
               logger.info("Removed student {} from classroom {}", studentId, classId);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public Optional<Classroom> getClassroomForStudent(Long classId, String studentId) {
      return this.studentRepository.findById(studentId).flatMap((student) -> {
         return student.getClassrooms().stream().filter((c) -> {
            return c.getId().equals(classId);
         }).findFirst().flatMap((c) -> {
            return this.classroomRepository.findById(classId);
         });
      });
   }
}
  