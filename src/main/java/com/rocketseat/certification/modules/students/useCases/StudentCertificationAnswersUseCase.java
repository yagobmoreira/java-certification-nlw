package com.rocketseat.certification.modules.students.useCases;

import com.rocketseat.certification.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification.modules.questions.repositories.QuestionRespository;
import com.rocketseat.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.rocketseat.certification.modules.students.dto.VerifyHasCertificationDTO;
import com.rocketseat.certification.modules.students.entities.AnswersCertificationsEntity;
import com.rocketseat.certification.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification.modules.students.entities.StudentEntity;
import com.rocketseat.certification.modules.students.repositories.CertificationStudentRepository;
import com.rocketseat.certification.modules.students.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCertificationAnswersUseCase {


  private final StudentRepository studentRepository;
  private final QuestionRespository questionRepository;
  private final CertificationStudentRepository certificationStudentRepository;
  private final VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  @Autowired
  public StudentCertificationAnswersUseCase(StudentRepository studentRepository,
      QuestionRespository questionRespository,
      CertificationStudentRepository certificationStudentRepository,
      VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase) {
    this.studentRepository = studentRepository;
    this.questionRepository = questionRespository;
    this.certificationStudentRepository = certificationStudentRepository;
    this.verifyIfHasCertificationUseCase = verifyIfHasCertificationUseCase;
  }

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

    var hasCertification = this.verifyIfHasCertificationUseCase
        .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

    if (hasCertification) {
      throw new Exception("Você já tirou sua certificação!");
    }

    List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
    List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

    AtomicInteger correctAnswers = new AtomicInteger(0);

    dto.getQuestionsAnswers()
        .stream().forEach(questionAnswer -> {
          var question = questionsEntity.stream()
              .filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

          var findCorrectAlternative = question.getAlternatives().stream()
              .filter(alternative -> alternative.isCorrect()).findFirst().get();

          if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
            questionAnswer.setCorrect(true);
            correctAnswers.incrementAndGet();
          } else {
            questionAnswer.setCorrect(false);
          }

          var answerrsCertificationsEntity = AnswersCertificationsEntity.builder()
              .answerID(questionAnswer.getAlternativeID())
              .questionID(questionAnswer.getQuestionID())
              .isCorrect(questionAnswer.isCorrect()).build();

          answersCertifications.add(answerrsCertificationsEntity);
        });

    var student = studentRepository.findByEmail(dto.getEmail());
    UUID studentID;
    if (student.isEmpty()) {
      var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
      studentCreated = studentRepository.save(studentCreated);
      studentID = studentCreated.getId();
    } else {
      studentID = student.get().getId();
    }

    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
        .technology(dto.getTechnology())
        .studentID(studentID)
        .grade(correctAnswers.get())
        .build();

    var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

    answersCertifications.stream().forEach(answerCertification -> {
      answerCertification.setCertificationID(certificationStudentEntity.getId());
      answerCertification.setCertificationStudentEntity(certificationStudentEntity);
    });

    certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);

    certificationStudentRepository.save(certificationStudentEntity);

    return certificationStudentCreated;
  }
}