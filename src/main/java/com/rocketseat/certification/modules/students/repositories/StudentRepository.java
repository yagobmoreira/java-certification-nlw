package com.rocketseat.certification.modules.students.repositories;

import com.rocketseat.certification.modules.students.entities.StudentEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
  public Optional<StudentEntity> findByEmail(String email);
}
