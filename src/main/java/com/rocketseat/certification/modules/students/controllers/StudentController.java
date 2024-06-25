package com.rocketseat.certification.modules.students.controllers;

import com.rocketseat.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.rocketseat.certification.modules.students.dto.VerifyHasCertificationDTO;
import com.rocketseat.certification.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.rocketseat.certification.modules.students.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/students")
public class StudentController {

  private final VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
  private final StudentCertificationAnswersUseCase studentCertificationAnswerUseCase;

  @Autowired
  public StudentController(VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase,
     StudentCertificationAnswersUseCase studentCertificationAnswerUseCase) {
    this.verifyIfHasCertificationUseCase = verifyIfHasCertificationUseCase;
    this.studentCertificationAnswerUseCase = studentCertificationAnswerUseCase;
  }


  @PostMapping("/verifyIfHasCertification")
  public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
    var result = verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);
    if (result) {
      return "Usuário já fez a prova";
    }

    return "Usuário pode fazer a prova";
  }

  @PostMapping("/certification/answer")
  public ResponseEntity<Object> certificationAnswer(
      @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
    try {
      var result = studentCertificationAnswerUseCase.execute(studentCertificationAnswerDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}