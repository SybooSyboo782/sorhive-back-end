package com.sorhive.comprojectserver.feed.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 * Class : FeedResponseDto
 * Comment: 피드 응답 전송 객체
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
@Setter
public class FeedResponseDto {

    private FeedSummary feedSummary;
    private List<FeedCommentSummary> feedCommentSummaryList;
    private List<FeedImageSummary> feedImageSummaryList;

}
