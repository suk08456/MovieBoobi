package com.korea.MOVIEBOOK.customerSupport.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateForm {

    @Size(min = 2, message = "제목은 최소 2자 이상이어야 합니다.")
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @Size(min = 2, message = "내용은 최소 2자 이상이어야 합니다.")
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
