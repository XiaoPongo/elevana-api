package com.elevana_api.service;

import com.elevana_api.model.Classroom;
import com.elevana_api.model.Student;
import com.elevana_api.repository.ClassroomRepository;
import com.elevana_api.repository.StudentRepository;

import java.util.List;
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

    /**
     * Fetch an existing student or create a new one if missing.
     */
    @Transactional
    public Student getOrCreateStudent(String studentId, String email, String firstName, String lastName) {
        return studentRepository.findById(studentId).orElseGet(() -> {
            logger.info("Creating new student profile for user ID: {}", studentId);
            Student newStudent = new Student();
            newStudent.setId(studentId);
            newStudent.setEmail(email);
            newStudent.setFirstName(firstName);
            newStudent.setLastName(lastName);
            newStudent.setXp(0);
            return studentRepository.save(newStudent);
        });
    }

    /**
     * Allow a student to join a classroom by classCode.
     */
    @Transactional
    public Classroom joinClass(String studentId, String email, String firstName, String lastName, String classCode) {
        Student student = this.getOrCreateStudent(studentId, email, firstName, lastName);

        Optional<Classroom> classroomOpt = classroomRepository.findByClassCode(classCode);
        if (classroomOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid class code.");
        }

        Classroom classroom = classroomOpt.get();
        if (!classroom.isAllowNewStudents()) {
            throw new IllegalStateException("This class is not currently accepting new students.");
        }

        // Add student if not already in the class
        if (!student.getClassrooms().contains(classroom)) {
            student.getClassrooms().add(classroom);
            studentRepository.save(student);
            logger.info("Student {} successfully joined class {}", studentId, classroom.getId());
        } else {
            logger.info("Student {} is already enrolled in class {}", studentId, classroom.getId());
        }

        return classroom;
    }

    /**
     * Add XP to a student.
     */
    @Transactional
    public Student addXp(String studentId, int xpToAdd) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student profile not found."));

        student.setXp(student.getXp() + xpToAdd);
        logger.info("Adding {} XP to student {}. New total: {}", xpToAdd, studentId, student.getXp());
        return studentRepository.save(student);
    }

    /**
     * Fetch all classes a student is enrolled in.
     */
    @Transactional(readOnly = true)
    public List<Classroom> getStudentClasses(String studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        return classroomRepository.findByStudentsContaining(student);
    }
}
