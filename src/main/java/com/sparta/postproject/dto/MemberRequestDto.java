package com.sparta.postproject.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class MemberRequestDto {
    @Pattern(regexp="[0-9a-z]{4,10}",
            message = "아이디는 영문 소문자와 숫자로 이루어진 4~10자의 아이디여야 합니다.")
    private String username;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 15자의 비밀번호여야 합니다.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";


}
