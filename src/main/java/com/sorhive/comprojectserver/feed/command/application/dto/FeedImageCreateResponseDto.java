package com.sorhive.comprojectserver.feed.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : FeedImageResponseDto
 * Comment: 피드 이미지 작성 응답 객체
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
public class FeedImageCreateResponseDto {

    private Long feedImageId;
    private Timestamp uploadTime;
    private String feedImagePath;

    public FeedImageCreateResponseDto(Long id, Timestamp uploadTime, String path) {

        this.feedImageId = id;
        this.uploadTime = uploadTime;
        this.feedImagePath = path;

    }
}
