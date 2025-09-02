package com.sorhive.comprojectserver.feed.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class : FeedCreateResponseDto
 * Comment: 피드 작성 응답 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FeedCreateResponseDto {

    private Long feedId;
    private String feedContent;
    private Timestamp feedCreateTime;

    public FeedCreateResponseDto(Long feedId, LocalDateTime feedCreateTime, String feedContent) {

        this.feedId = feedId;
        this.feedCreateTime = Timestamp.valueOf(feedCreateTime);
        this.feedContent = feedContent;

    }

}
