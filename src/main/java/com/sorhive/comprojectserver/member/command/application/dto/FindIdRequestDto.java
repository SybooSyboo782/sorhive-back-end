package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 * Class : FindIdRequestDto
 * Comment: 아이디 찾기 요청 전송 객체
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
public class FindIdRequestDto {

    @NotEmpty(message = "이메일이 없습니다.")
    @Email
    private String email;

    @NotEmpty(message = "이름이 없습니다.")
    private String name;

}
