package com.sorhive.comprojectserver.room.command.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * <pre>
 * Class : GuestBookUpdateRequestDto
 * Comment: 방명록 수정 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-16       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
public class GuestBookUpdateRequestDto {

    @NotNull
    private Long guestBookId;

    @NotNull
    private String content;

}
