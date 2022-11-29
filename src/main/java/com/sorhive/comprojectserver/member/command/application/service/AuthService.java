package com.sorhive.comprojectserver.member.command.application.service;

import com.sorhive.comprojectserver.common.pattern.CustomPattern;
import com.sorhive.comprojectserver.config.dto.TokenDto;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.application.dto.LoginDto;
import com.sorhive.comprojectserver.member.command.application.dto.SignUpDto;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberId;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.IdPatternNotMatchedException;
import com.sorhive.comprojectserver.member.command.exception.LoginFailedException;
import com.sorhive.comprojectserver.member.command.exception.PasswordPatternNotMatchedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Class : AuthService
 * Comment: 인증 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-06       부시연           최초 생성
 * 2022-11-06       부시연           회원 가입
 * 2022-11-07       부시연           로그인
 * 2022-11-24       부시연           아이디 중복검사 추가
 * 2022-11-24       부시연           dPdhlcjfl
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class AuthService {

    private final CustomPattern customPattern;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthService(CustomPattern customPattern, MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.customPattern = customPattern;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    /** 회원 가입 */
    @Transactional
    public Long signup(SignUpDto signUpDto) {
        log.info("[AuthService] Signup Start ===================================");
        log.info("[AuthService] signUpDto {}", signUpDto);

        if(!customPattern.idPattern(signUpDto.getMemberId())) {
            throw new IdPatternNotMatchedException();
        }

        if(!customPattern.passwordPattern(signUpDto.getPassword())) {
            throw new PasswordPatternNotMatchedException();
        }

        Member member = new Member(
                new MemberId(signUpDto.getMemberId()),
                signUpDto.getMemberName(),
                passwordEncoder.encode(signUpDto.getPassword())
        );

        memberRepository.save(member);

        log.info("[AuthService] Signup End ==============================");

        return member.getMemberCode();
    }

    /** 로그인 */
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", loginDto);
        System.out.println(loginDto.getPassword().toString());

        Member member;

        /* 1. 아이디 조회 */
        if(memberRepository.findByMemberId(loginDto.getMemberId()) != null){
            member = memberRepository.findByMemberId(loginDto.getMemberId());
        } else {
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        /* 2. 비밀번호 매칭 */
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword().toString())) {
            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        /* 3. 토큰 발급 */
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        log.info("[AuthService] tokenDto {}", tokenDto);

        log.info("[AuthService] Login End ===================================");

        return tokenDto;

    }

    /** 아이디 중복검사 */
    public String idCheck(String newMemberId) {

        log.info("[AuthQueryService] idCheck Start =================");
        log.info("[AuthQueryService] newMemberId " + newMemberId);

        String idCheck = "";

        if(memberRepository.findByMemberId(MemberId.of(newMemberId)) == null) {
            idCheck = "중복된 아이디가 없습니다";
        } else {
            idCheck = "중복된 아이디가 존재합니다 !";
        }

        return idCheck;

    }

}
