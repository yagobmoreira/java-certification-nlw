package com.rocketseat.certification.modules.certifications.useCases;

import com.rocketseat.certification.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification.modules.students.repositories.CertificationStudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Top10RankingUseCase {
  private final CertificationStudentRepository certificationStudentRepository;

  @Autowired
  public Top10RankingUseCase(CertificationStudentRepository certificationStudentRepository) {
    this.certificationStudentRepository = certificationStudentRepository;
  }

  public List<CertificationStudentEntity> execute() {
    return this.certificationStudentRepository.findTop10ByOrderByGradeDesc();
  }
}
