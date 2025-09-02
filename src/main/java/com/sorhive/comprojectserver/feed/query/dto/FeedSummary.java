package com.sorhive.comprojectserver.feed.query.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

/**
 * <pre>
 * Class : FeedSummary
 * Comment: 피드 요약 전송 객체
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
public class FeedSummary {

    private Long feedId;
    private boolean deleted;
    private String feedWriterName;
    private String feedWriterId;
    private String feedContent;
    private Timestamp feedCreateTime;

    private List<FeedCommentSummary> feedCommentSummary;
    private List<FeedImageSummary> feedImageSummary;

}
