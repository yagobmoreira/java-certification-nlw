package com.rocketseat.certification.modules.questions.repositories;

import com.rocketseat.certification.modules.questions.entities.AlternativesEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativesRepository extends JpaRepository<AlternativesEntity, UUID> {

}
