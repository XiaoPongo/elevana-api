package com.elevana_api.controller;

import com.elevana_api.model.Classroom;
import com.elevana_api.model.Student;
import com.elevana_api.service.StudentService;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ✅ Only keep this version
    @PostMapping("/join-class")
    public ResponseEntity<?> joinClassCompat(@RequestBody Map<String, String> payload, 
                                             @AuthenticationPrincipal Jwt jwt) {
        String classCode = payload.containsKey("code") ? payload.get("code") : payload.get("classCode");
        String studentId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("user_metadata.firstName");
        String lastName = jwt.getClaimAsString("user_metadata.lastName");

        try {
            Classroom joinedClass = this.studentService.joinClass(studentId, email, firstName, lastName, classCode);
            return ResponseEntity.ok(joinedClass);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getProfile(@AuthenticationPrincipal Jwt jwt) {
        String studentId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("user_metadata.firstName");
        String lastName = jwt.getClaimAsString("user_metadata.lastName");
        Student student = this.studentService.getOrCreateStudent(studentId, email, firstName, lastName);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/classes")
    public ResponseEntity<Set<Classroom>> getClasses(@AuthenticationPrincipal Jwt jwt) {
        String studentId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("user_metadata.firstName");
        String lastName = jwt.getClaimAsString("user_metadata.lastName");
        Student student = this.studentService.getOrCreateStudent(studentId, email, firstName, lastName);
        return ResponseEntity.ok(student.getClassrooms());
    }

    @PostMapping("/add-xp")
    public ResponseEntity<?> addXpToStudent(@RequestBody Map<String, Integer> payload, @AuthenticationPrincipal Jwt jwt) {
        String studentId = jwt.getSubject();
        Integer xpToAdd = payload.get("xp");
        if (xpToAdd == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "XP amount is required."));
        }
        try {
            Student updatedStudent = this.studentService.addXp(studentId, xpToAdd);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
