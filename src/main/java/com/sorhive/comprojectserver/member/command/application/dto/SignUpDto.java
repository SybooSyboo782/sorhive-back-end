package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 * Class : SignUpDto
 * Comment: 회원 생성 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-06       부시연           최초 생성
 * 2022-11-24       부시연           이메일 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
public class SignUpDto {

    @NotBlank
    private String memberId;

    @NotBlank
    @Length(max = 15, message = "회원 닉네임 길이가 너무 깁니다.")
    private String memberName;

    @NotBlank
    private String password;

    @NotEmpty(message = "이메일이 없습니다.")
    @Email
    private String email;

    protected SignUpDto() {}
}
