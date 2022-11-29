package com.sorhive.comprojectserver.member.command.application.controller;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.member.command.application.dto.*;
import com.sorhive.comprojectserver.member.command.application.service.AuthService;
import com.sorhive.comprojectserver.member.command.infra.AuthInfraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

/**
 * <pre>
 * Class : AuthController
 * Comment: 인증 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-06       부시연           최초 생성
 * 2022-11-06       부시연           회원 가입
 * 2022-11-06       부시연           로그인
 * 2022-11-18       부시연           이메일 인증 추가
 * 2022-11-20       부시연           아이디 찾기 추가
 * 2022-11-21       부시연           비밀번호 재설정 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthInfraService authInfraService;

    public AuthController(AuthService authService, AuthInfraService authInfraService) {
        this.authService = authService;
        this.authInfraService = authInfraService;
    }

    /** 회원가입 */
    @PostMapping("signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpDto signUpDto) {

        log.info("[AuthController] signup Start ======================");
        log.info("[AuthController] signUpDto : " + signUpDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회원가입 성공", authService.signup(signUpDto)));
    }

    /** 로그인 */
    @PutMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {

        log.info("[AuthController] login Start ======================");
        log.info("[AuthController] loginDto : " + loginDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "로그인 성공", authService.login(loginDto)));
        
    }

    /** 이메일 인증 */
    @PostMapping("email")
    public ResponseEntity<ResponseDto> emailAuthentication(@Valid @RequestBody EmailRequestDto emailRequestDto) throws MessagingException {

        log.info("[AuthController] emailAuthentication Start =================");
        log.info("[AuthController] emailRequestDto " + emailRequestDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 이메일 중복 검사 및 인증 성공", authInfraService.emailAuthentication(emailRequestDto)));
    }

    /** 아이디 검사 */
    @PostMapping("id")
    public ResponseEntity<ResponseDto> idCheck(@Valid @RequestBody IdRequestDto idRequestDto) {

        log.info("[AuthQueryController] idCheck Start =================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 아이디 검사", authService.idCheck(idRequestDto.getMemberId())));
    }

    /** 아이디 찾기 */
    @PostMapping("find/id")
    public ResponseEntity<ResponseDto> findId(@Valid @RequestBody FindIdRequestDto findIdRequestDto) throws MessagingException {

        log.info("[AuthController] findId Start =================");
        log.info("[AuthController] findIdRequestDto " + findIdRequestDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "아이디 찾기 성공", authInfraService.findId(findIdRequestDto)));
    }

    /** 비밀번호 재설정 */
    @PostMapping("reset/password")
    public ResponseEntity<ResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) throws MessagingException {

        log.info("[AuthController] resetPassword Start =================");
        log.info("[AuthController] emailRequestDto " + resetPasswordRequestDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "비밀번호 재설정 성공", authInfraService.resetPassword(resetPasswordRequestDto)));
    }

}
