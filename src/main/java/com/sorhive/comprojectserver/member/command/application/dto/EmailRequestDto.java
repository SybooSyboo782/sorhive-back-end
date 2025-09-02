package com.sorhive.comprojectserver.member.command.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

/**
 * <pre>
 * Class : EmailRequestDto
 * Comment: 이메일 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class EmailRequestDto {

    @NotEmpty(message = "이메일이 없습니다.")
    @Email
    private String email;
}
