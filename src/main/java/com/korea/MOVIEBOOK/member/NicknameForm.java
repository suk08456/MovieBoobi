package com.korea.MOVIEBOOK.member;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameForm {


    @NotBlank(message = "닉네임은 필수입니다.")
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[가-힣a-z0-9]{3,20}$")
    private String newNickname;
}
