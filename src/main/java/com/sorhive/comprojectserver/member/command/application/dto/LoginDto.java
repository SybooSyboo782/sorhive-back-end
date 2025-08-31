package com.sorhive.comprojectserver.member.command.application.dto;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Class : LoginDto
 * Comment: 로그인 전송객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-06       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@AllArgsConstructor
@Getter
public class LoginDto {

    @NotNull
    private MemberId memberId;

    @NotNull
    private String password;

    protected LoginDto() { }
}
