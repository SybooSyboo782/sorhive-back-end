package com.sorhive.comprojectserver.chatting.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * <pre>
 * Class : ChattingDetailResponseDto
 * Comment: 채팅 상세 조회 응답 전송 객체
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
public class ChattingDetailResponseDto {

    private Long memberCode1;
    private String memberName1;
    private String memberRoomImage1;
    private Long memberCode2;
    private String memberName2;
    private String memberRoomImage2;
    private String lastMessage;
    private LocalDateTime uploadTime;

    public ChattingDetailResponseDto(Long memberCode1, String memberName1, String memberRoomImage1, Long memberCode2, String memberName2, String memberRoomImage2, String lastMessage, LocalDateTime uploadTime) {
        this.memberCode1 = memberCode1;
        this.memberName1 = memberName1;
        this.memberRoomImage1 = memberRoomImage1;
        this.memberCode2 = memberCode2;
        this.memberName2 = memberName2;
        this.memberRoomImage2 = memberRoomImage2;
        this.lastMessage = lastMessage;
        this.uploadTime = uploadTime;
    }
}
