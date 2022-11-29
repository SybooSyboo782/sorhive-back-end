package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 * Class : ResetPasswordRequestDto
 * Comment: 패스워드 재설정 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class ResetPasswordRequestDto {

    @NotEmpty(message = "이메일이 없습니다.")
    @Email
    private String email;

    @NotEmpty(message = "이름이 없습니다.")
    private String name;
}
