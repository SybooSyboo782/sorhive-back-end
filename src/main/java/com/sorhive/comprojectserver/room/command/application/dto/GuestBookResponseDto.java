package com.sorhive.comprojectserver.room.command.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * <pre>
 * Class : GuestBookCreateResponseDto
 * Comment: 방명록 생성 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * 2022-11-16       부시연           방명록에 생성, 수정에 대한 응답 전송 객체로 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class GuestBookResponseDto {

    private Long guestBookId;
    private String guestBookContent;
    private Long guestBookWriterCode;
    private String guestBookWriterId;
    private String guestBookWriterName;
    private Long roomId;
    private LocalDateTime createTime;

    public GuestBookResponseDto(Long guestBookId, String guestBookContent, Long guestBookWriterCode, String guestBookWriterId, String guestBookWriterName, Long roomId, LocalDateTime createTime) {
        this.guestBookId = guestBookId;
        this.guestBookContent = guestBookContent;
        this.guestBookWriterCode = guestBookWriterCode;
        this.guestBookWriterId = guestBookWriterId;
        this.guestBookWriterName = guestBookWriterName;
        this.roomId = roomId;
        this.createTime = createTime;
    }
}
