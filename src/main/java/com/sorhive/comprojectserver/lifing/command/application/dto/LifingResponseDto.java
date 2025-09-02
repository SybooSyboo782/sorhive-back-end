package com.sorhive.comprojectserver.lifing.command.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class : ResponseLifingDto
 * Comment: 라이핑 생성 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-16       부시연           라이핑 이미지 리스트로 변경
 * 2022-11-19       부시연           라이핑 이미지 문자열로 다시 변경
 * </pre>
 * @version 1(클래스 버전)
 * @author 부시연(최초 작성자)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LifingResponseDto {

    private Long lifingId;
    private String lifingContent;
    private Long lifingNo;
    private Long lifingCategoryNo;
    private Timestamp lifingCreateTime;
    private Long memberCode;
    private String lifingWriterName;
    private String lifingWriterId;
    private String lifingImagePath;

    public LifingResponseDto(Long lifingId, String lifingConetent, Long lifingNo, Long lifingCategoryNo, LocalDateTime createTime,
                             Long memberCode, String lifingWriterName, String lifingWriterId, String lifingImagePath) {

        this.lifingId = lifingId;
        this.lifingContent = lifingConetent;
        this.lifingNo = lifingNo;
        this.lifingCategoryNo = lifingCategoryNo;
        this.lifingCreateTime = Timestamp.valueOf(createTime);
        this.memberCode = memberCode;
        this.lifingWriterName = lifingWriterName;
        this.lifingWriterId = lifingWriterId;
        this.lifingImagePath = lifingImagePath;

    }

    public LifingResponseDto(Long lifingId, String lifingConetent, Long lifingNo, Long lifingCategoryNo, LocalDateTime createTime,
                             Long memberCode, String lifingWriterName, String lifingWriterId) {

        this.lifingId = lifingId;
        this.lifingContent = lifingConetent;
        this.lifingNo = lifingNo;
        this.lifingCategoryNo = lifingCategoryNo;
        this.lifingCreateTime = Timestamp.valueOf(createTime);
        this.memberCode = memberCode;
        this.lifingWriterName = lifingWriterName;
        this.lifingWriterId = lifingWriterId;
    }
}
