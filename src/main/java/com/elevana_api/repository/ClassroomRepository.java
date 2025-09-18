    package com.elevana_api.repository;

import com.elevana_api.model.Classroom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.elevana_api.model.Student;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
   List<Classroom> findByMentorId(String mentorId);
   List<Classroom> findByStudentsContaining(Student student);

   Optional<Classroom> findByIdAndMentorId(Long id, String mentorId);

   Optional<Classroom> findByClassCode(String classCode);
   
   boolean existsByIdAndMentorId(Long id, String mentorId);
}
    