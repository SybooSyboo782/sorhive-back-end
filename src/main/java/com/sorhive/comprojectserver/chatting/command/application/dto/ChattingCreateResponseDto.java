package com.sorhive.comprojectserver.chatting.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : ChattingCreateResponseDto
 * Comment: 채팅 생성 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * 2022-11-21       부시연           마지막 채팅 건에 대한 응답 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class ChattingCreateResponseDto {

    private final Long chattingNo;
    private final Long memberCode1;
    private final Long memberCode2;
    private final String mongoId;
    private final Timestamp uploadTime;


    public ChattingCreateResponseDto(Long chattingNo, Long memberCode1, Long memberCode2, String chattingId, Timestamp uploadTime) {
        this.chattingNo = chattingNo;
        this.memberCode1 = memberCode1;
        this.memberCode2 = memberCode2;
        this.mongoId = chattingId;
        this.uploadTime = uploadTime;
    }
}
