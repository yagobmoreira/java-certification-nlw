package com.rocketseat.certification.modules.questions.controllers;

import com.rocketseat.certification.modules.questions.dto.AlternativesResultDTO;
import com.rocketseat.certification.modules.questions.dto.QuestionResultDTO;
import com.rocketseat.certification.modules.questions.entities.AlternativesEntity;
import com.rocketseat.certification.modules.questions.entities.QuestionEntity;
import com.rocketseat.certification.modules.questions.repositories.QuestionRespository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  private final QuestionRespository questionRespository;

  @Autowired
  public QuestionController(QuestionRespository questionRespository) {
    this.questionRespository = questionRespository;
  }

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
    var result = questionRespository.findByTechnology(technology);
    return result.stream().map(QuestionController::mapQuestionToDTO)
        .collect(Collectors.toList());
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
    var questionResultDTO = QuestionResultDTO.builder()
        .id(question.getId())
        .technology(question.getTechnology())
        .description(question.getDescription())
        .build();

    List<AlternativesResultDTO> alternativesResultDTOS = question.getAlternatives()
        .stream().map(QuestionController::mapAlternativeDTO)
        .toList();

    questionResultDTO.setAlternatives(alternativesResultDTOS);
    return questionResultDTO;
  }

  static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternatives) {
    return AlternativesResultDTO.builder()
        .id(alternatives.getId())
        .description(alternatives.getDescription())
        .build();
  }
}
