    package com.elevana_api.service;

import com.elevana_api.model.Classroom;
import com.elevana_api.model.Student;
import com.elevana_api.repository.ClassroomRepository;
import com.elevana_api.repository.StudentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
   private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
   @Autowired
   private StudentRepository studentRepository;
   @Autowired
   private ClassroomRepository classroomRepository;

   @Transactional
   public Student getOrCreateStudent(String studentId, String email, String firstName, String lastName) {
      return (Student)this.studentRepository.findById(studentId).orElseGet(() -> {
         logger.info("Creating new student profile for user ID: {}", studentId);
         Student newStudent = new Student();
         newStudent.setId(studentId);
         newStudent.setEmail(email);
         newStudent.setFirstName(firstName);
         newStudent.setLastName(lastName);
         return (Student)this.studentRepository.save(newStudent);
      });
   }

   @Transactional
   public Classroom joinClass(String studentId, String email, String firstName, String lastName, String classCode) {
      Student student = this.getOrCreateStudent(studentId, email, firstName, lastName);
      Optional<Classroom> classroomOpt = this.classroomRepository.findByClassCode(classCode);
      if (classroomOpt.isEmpty()) {
         throw new IllegalArgumentException("Invalid class code.");
      } else {
         Classroom classroom = (Classroom)classroomOpt.get();
         if (!classroom.isAllowNewStudents()) {
            throw new IllegalStateException("This class is not currently accepting new students.");
         } else {
            student.getClassrooms().add(classroom);
            this.studentRepository.save(student);
            logger.info("Student {} successfully joined class {}", studentId, classroom.getId());
            return classroom;
         }
      }
   }

   @Transactional
   public Student addXp(String studentId, int xpToAdd) {
      Student student = (Student)this.studentRepository.findById(studentId).orElseThrow(() -> {
         return new IllegalArgumentException("Student profile not found.");
      });
      student.setXp(student.getXp() + xpToAdd);
      logger.info("Adding {} XP to student {}. New total: {}", new Object[]{xpToAdd, studentId, student.getXp()});
      return (Student)this.studentRepository.save(student);
   }
}
   