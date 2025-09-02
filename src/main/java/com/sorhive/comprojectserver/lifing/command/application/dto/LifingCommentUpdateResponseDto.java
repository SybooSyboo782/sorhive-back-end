package com.sorhive.comprojectserver.lifing.command.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class : LifingCommentUpdateResponseDto
 * Comment: 라이핑 댓글 수정 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
@EqualsAndHashCode
public class LifingCommentUpdateResponseDto {

    @NotNull(message = "해당 댓글 수정에 필요한 내용이 없습니다.")
    private Long lifingCommentId;

    @NotNull(message = "해당 댓글 수정에 필요한 내용이 없습니다.")
    private String lifingCommentContent;

    private Long lifingCommentWriterCode;

    private String lifingCommentWriterName;

    private String lifingCommentWriterId;

    private Timestamp lifingCommentUploadTime;

    public LifingCommentUpdateResponseDto(Long lifingCommentId, String lifingCommentContent, Long lifingCommentWriterCode,
                                          String lifingCommentWriterName, String lifingCommentWriterId, LocalDateTime lifingCommentUploadTime) {
        this.lifingCommentId = lifingCommentId;
        this.lifingCommentContent = lifingCommentContent;
        this.lifingCommentWriterCode = lifingCommentWriterCode;
        this.lifingCommentWriterName = lifingCommentWriterName;
        this.lifingCommentWriterId = lifingCommentWriterId;
        this.lifingCommentUploadTime = Timestamp.valueOf(lifingCommentUploadTime);
    }
}
