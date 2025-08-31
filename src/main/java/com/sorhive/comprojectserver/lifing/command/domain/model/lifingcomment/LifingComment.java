package com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.Lifing;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : LifingComment
 * Comment: 라이핑 댓글 도메인
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-15       부시연           최초 생성
 * 2022-11-15       부시연           라이핑 댓글 삭제
 * 2022-11-15       부시연           라이핑 댓글 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_lifing_comments")
@Getter
public class LifingComment {

    @Id
    @Column(name="lifing_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifingCommentId;

    @Column(name = "lifing_comment_content")
    private String lifingCommentContent;

    private LifingCommentWriter lifingCommentWriter;

    @Column(name = "lifing_comment_create_time")
    private Timestamp lifingCommentCreateTime;

    @Column(name = "lifing_comment_upload_time")
    private Timestamp lifingCommentUploadTime;

    @Column(name = "lifing_comment_delete_time")
    private Timestamp lifingCommentDeleteTime;

    @Column(name = "lifing_comment_delete_yn")
    @ColumnDefault(value = "'N'")
    private Character lifingCommentDeleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_lifing_id")
    private Lifing lifing;

    protected LifingComment() {}

    public LifingComment(LifingCommentWriter lifingCommentWriter, String lifingCommentContent, Lifing lifing) {
        this.lifingCommentWriter = lifingCommentWriter;
        this.lifingCommentContent = lifingCommentContent;
        this.lifingCommentCreateTime = new Timestamp(System.currentTimeMillis());
        this.lifingCommentUploadTime = new Timestamp(System.currentTimeMillis());
        this.lifingCommentDeleteYn = 'N';
        this.lifing = lifing;
    }

    /** 라이핑 댓글 삭제 */
    public void deleteComment(char deleteYn) {
        this.lifingCommentDeleteTime = new Timestamp(System.currentTimeMillis());
        this.lifingCommentDeleteYn = deleteYn;
    }

    /** 라이핑 댓글 수정 */
    public void updateComment(String lifingCommentContent) {
        this.lifingCommentContent = lifingCommentContent;
        this.lifingCommentUploadTime = new Timestamp(System.currentTimeMillis());
    }
}
