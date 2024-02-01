package com.korea.MOVIEBOOK.member;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameForm {


    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 8)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "한글, 영문 대,소문자, 숫자를 사용하여 2 ~ 8길이로 설정 해주세요.")
    private String newNickname;
}
