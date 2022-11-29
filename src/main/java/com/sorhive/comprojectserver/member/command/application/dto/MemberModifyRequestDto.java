package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class : MemberModifyRequestDto
 * Comment: 회원 수정 요청 전송 객체
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
public class MemberModifyRequestDto {

    @NotNull
    private String name;

}
