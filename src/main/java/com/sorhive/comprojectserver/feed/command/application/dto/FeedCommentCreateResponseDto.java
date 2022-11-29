package com.sorhive.comprojectserver.feed.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : FeedCommentCreateResponseDto
 * Comment: 피드 댓글 응답 전송 객체
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
public class FeedCommentCreateResponseDto {

    private Long feedCommentId;
    private String feedCommentcontent;
    private Timestamp feedCommentCreateTime;

    public FeedCommentCreateResponseDto(Long id, String content, Timestamp createTime) {

        this.feedCommentId = id;
        this.feedCommentcontent = content;
        this.feedCommentCreateTime = createTime;

    }
}
