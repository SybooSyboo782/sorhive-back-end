package com.sorhive.comprojectserver.lifing.command.application.dto;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment.LifingCommentWriter;
import lombok.Getter;

import java.sql.Timestamp;

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
public class LifingCommentUpdateResponseDto {

    private Long lifingCommentId;
    private String lifingCommentContent;
    private LifingCommentWriter lifingCommentWriter;
    private Timestamp lifingCommentUploadTime;

    public LifingCommentUpdateResponseDto(Long lifingCommentId, String lifingCommentContent, LifingCommentWriter lifingCommentWriter, Timestamp lifingCommentUploadTime) {
        this.lifingCommentId = lifingCommentId;
        this.lifingCommentContent = lifingCommentContent;
        this.lifingCommentWriter = lifingCommentWriter;
        this.lifingCommentUploadTime = lifingCommentUploadTime;
    }
}
