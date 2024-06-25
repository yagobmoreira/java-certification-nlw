package com.rocketseat.certification.modules.students.useCases;

import com.rocketseat.certification.modules.students.dto.VerifyHasCertificationDTO;
import com.rocketseat.certification.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {
  private final CertificationStudentRepository repository;

  @Autowired
  public VerifyIfHasCertificationUseCase(CertificationStudentRepository repository) {
    this.repository = repository;
  }

  public boolean execute(VerifyHasCertificationDTO dto) {
    var result = this.repository.findByStudentEmailAndTechnology(dto.getEmail(),
        dto.getTechnology());
    if (!result.isEmpty()) {
      return true;
    }
    return false;
  }
}
