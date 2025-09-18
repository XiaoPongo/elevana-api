    package com.elevana_api.repository;

import com.elevana_api.model.Activity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
   List<Activity> findByClassroomId(Long classroomId);
}
 