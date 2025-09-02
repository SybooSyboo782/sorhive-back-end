package com.sorhive.comprojectserver.feed.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class : FeedModifyResponseDto
 * Comment: 피드 수정 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FeedModifyResponseDto {

    private Long feedId;
    private Long memberCode;
    private String feedWriterName;
    private String feedWriterId;
    private String feedContent;
    private Timestamp feedUploadTime;

    public FeedModifyResponseDto(Long feedId, String feedContent, Long memberCode, String feedWriterName,
                                 String feedWriterId, LocalDateTime feedUploadTime) {
        this.feedId = feedId;
        this.feedContent = feedContent;
        this.memberCode = memberCode;
        this.feedWriterName = feedWriterName;
        this.feedWriterId = feedWriterId;
        this.feedUploadTime = Timestamp.valueOf(feedUploadTime);

    }
}
