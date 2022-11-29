package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;
import lombok.NonNull;

/**
 * <pre>
 * Class : PasswordModifyRequestDto
 * Comment: 비밀번호 수정 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class PasswordModifyRequestDto {

    @NonNull
    private String password;
    
    @NonNull
    private String newPassword;
}
