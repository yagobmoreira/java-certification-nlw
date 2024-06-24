package com.rocketseat.certification.modules.questions.repositories;

import com.rocketseat.certification.modules.questions.entities.QuestionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRespository extends JpaRepository<QuestionEntity, UUID> {
  List<QuestionEntity> findByTechnology(String technology);
}
