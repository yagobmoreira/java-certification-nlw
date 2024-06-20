package com.rocketseat.certification.modules.students.useCases;

import com.rocketseat.certification.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {
  public boolean execute(VerifyHasCertificationDTO dto){
    return dto.getEmail().equals("yagomoreira@gmail.com") && dto.getTechnology().equals("java");
  }
}
