    package com.elevana_api.repository;

import com.elevana_api.model.Material;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
   List<Material> findByMentorId(String mentorId);
}
 