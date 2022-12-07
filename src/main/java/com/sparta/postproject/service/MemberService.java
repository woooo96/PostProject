package com.sparta.postproject.service;

import com.sparta.postproject.dto.LoginRequestDto;
import com.sparta.postproject.dto.MemberRequestDto;
import com.sparta.postproject.dto.ResponseDto;
import com.sparta.postproject.entity.Member;
import com.sparta.postproject.entity.UserEnum;
import com.sparta.postproject.jwt.JwtUtil;
import com.sparta.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";  //ADMIN usertype으로 회원가입 위한 ADMIN 토큰 값



    @Transactional
    public ResponseDto createMember(MemberRequestDto requestDto) {
        String username= requestDto.getUsername();
        String password = requestDto.getPassword();

        //입력받은 username이 db에 중복으로 존재하는지 체크
        Optional<Member> found = memberRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //defulat usertype을 UserEnum.USER로 세팅하고, requestDto에 boolean타입의 Admin 값과 ADMIN TOKEN STRING 값을 확인하여 관리자로 회원가입 시킬지 확인
        UserEnum usertype = UserEnum.USER;
        if(requestDto.isAdmin()) {
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다");
            }
            usertype = UserEnum.ADMIN;
        }

        //Member 객체를 생성해서 memberRepository에 저장
        Member member = new Member(username, password, usertype);
        memberRepository.save(member);
        return new ResponseDto("회원 가입이 완료되었습니다.", HttpStatus.OK.value());
    }

    public ResponseDto loginMember(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //입력받은 requestDto
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        if(!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getUsertype()));
        return new ResponseDto("로그인 되었습니다.", HttpStatus.OK.value());
    }
}
