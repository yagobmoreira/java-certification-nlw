package com.rocketseat.certification.modules.certifications.controllers;

import com.rocketseat.certification.modules.certifications.useCases.Top10RankingUseCase;
import com.rocketseat.certification.modules.students.entities.CertificationStudentEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
public class RankingController {
  private final Top10RankingUseCase top10RankingUseCase;

  @Autowired
  public RankingController(Top10RankingUseCase top10RankingUseCase) {
    this.top10RankingUseCase = top10RankingUseCase;
  }


  @GetMapping("/top10")
  public List<CertificationStudentEntity> top10() {
    return top10RankingUseCase.execute();
  }
}
