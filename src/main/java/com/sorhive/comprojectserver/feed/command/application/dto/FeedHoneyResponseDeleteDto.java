package com.sorhive.comprojectserver.feed.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : FeedHoneyResponseDeleteDto
 * Comment: 허니 제거 응답 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-15       부시연           라이핑과의 구분을 위해 피드 허니로 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FeedHoneyResponseDeleteDto {

    private Long feedHoneyId;
    private Long memberCode;
    private Long feedId;
    private Timestamp deleteTime;

    public FeedHoneyResponseDeleteDto(Long id, Long feedId, Long memberCode, Timestamp deleteTime) {

        this.feedHoneyId = id;
        this.memberCode = memberCode;
        this.feedId = feedId;
        this.deleteTime = deleteTime;

    }
}
