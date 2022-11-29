package com.sorhive.comprojectserver.member.command.application.controller;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.member.command.application.dto.MemberModifyRequestDto;
import com.sorhive.comprojectserver.member.command.application.dto.PasswordModifyRequestDto;
import com.sorhive.comprojectserver.member.command.application.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 * Class : MemberController
 * Comment: 회원 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       부시연           최초 생성
 * 2022-11-21       부시연           회원 수정 기능 추가
 * 2022-11-21       부시연           비밀번호 수정 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class MemberController {
    
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    /** 회원 정보 수정 */
    @PutMapping("member/{memberCode}")
    public ResponseEntity<ResponseDto> modifyMember(@RequestHeader String Authorization, @Valid @RequestBody MemberModifyRequestDto memberModifyRequestDto ) {

        log.info("[AuthController] modifyMember Start ======================");
        log.info("[AuthController] memberModifyRequestDto : " + memberModifyRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회원 수정 성공", memberService.modifyMember(accessToken, memberModifyRequestDto)));
    }
    
    /** 비밀번호 변경 */
    @PutMapping("change/password")
    public ResponseEntity<ResponseDto> modifyPassword(@RequestHeader String Authorization, @Valid @RequestBody PasswordModifyRequestDto passwordModifyRequestDto ) {

        log.info("[AuthController] modifyPassword Start ======================");
        log.info("[AuthController] passwordModifyRequestDto : " + passwordModifyRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회원 수정 성공", memberService.modifyPassword(accessToken, passwordModifyRequestDto)));
    }
}
