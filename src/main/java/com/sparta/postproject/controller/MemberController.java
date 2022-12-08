package com.sparta.postproject.controller;

import com.sparta.postproject.dto.LoginRequestDto;
import com.sparta.postproject.dto.MemberRequestDto;
import com.sparta.postproject.dto.ResponseDto;
import com.sparta.postproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    //회원가입하기 (@RequestBody로 입력받은 값을 @Valid 어노테이션으로 id/passwd 패턴 확인)
    @PostMapping("/signup")
    public ResponseDto createMember(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    //로그인하기
    @PostMapping("/login")
    public ResponseDto loginMember(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.loginMember(requestDto, response);
    }


}
