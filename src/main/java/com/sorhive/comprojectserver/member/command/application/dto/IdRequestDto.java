package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Class : IdRequestDto
 * Comment: 아이디 중복검사 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       부시연           최초 생성
 * 2022-11-24       부시연           아이디 유효성 검사 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class IdRequestDto {

    @NotEmpty(message = "아이디가 없습니다.")
    @Pattern(regexp =  "^[_.a-zA-Z0-9]{1,20}$", message = "아이디명은 특수문자(-.)를 포함하여 20자 이내로 작성 가능합니다")
    private String memberId;
}
