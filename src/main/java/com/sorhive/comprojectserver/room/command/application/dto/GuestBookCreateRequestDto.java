package com.sorhive.comprojectserver.room.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class : GuestBookCreateDto
 * Comment: 방명록 생성 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-08       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
public class GuestBookCreateRequestDto {

    @NotNull
    private Long roomId;

    @NotNull
    private String guestBookContent;

}
