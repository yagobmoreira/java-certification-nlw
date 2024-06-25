package com.rocketseat.certification.modules.students.repositories;

import com.rocketseat.certification.modules.students.entities.CertificationStudentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationStudentRepository extends JpaRepository<CertificationStudentEntity, UUID> {
  @Query("SELECT c FROM certifications c INNER JOIN c.studentEntity std WHERE std.email = :email AND c.technology = :technology")
  List<CertificationStudentEntity> findByStudentEmailAndTechnology(String email, String technology);

  @Query("SELECT c from certifications c ORDER BY c.grade DESC LIMIT 10")
  List<CertificationStudentEntity> findTop10ByOrderByGradeDesc();
}
