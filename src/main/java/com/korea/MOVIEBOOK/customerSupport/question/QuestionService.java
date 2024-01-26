package com.korea.MOVIEBOOK.customerSupport.question;

import com.korea.MOVIEBOOK.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void create(Member member, String title, String content) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setWriteDate(LocalDateTime.now());
        question.setMember(member);
        questionRepository.save(question);
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    public List<Question> getMyQuestionList(Member member) {
        List<Question> myQuestion = questionRepository.findByMember(member);
        return myQuestion;
    }

    public Question findByQuestionId(Long questionId) {
        Question question = null;
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            question = optionalQuestion.get();
        }
        return question;
    }
}
