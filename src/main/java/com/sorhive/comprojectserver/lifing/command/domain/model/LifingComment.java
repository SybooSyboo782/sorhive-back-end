package com.sorhive.comprojectserver.lifing.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LifingComment extends SoftDeleteEntity {

    @Id
    @Column(name="lifing_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifingCommentId;

    @Column(name = "lifing_comment_content")
    private String lifingCommentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifing_comment_writer")
    private Member lifingCommentWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifing_id")
    private Lifing lifing;

    public LifingComment(Member lifingCommentWriter, String lifingCommentContent, Lifing lifing) {
        this.lifingCommentWriter = lifingCommentWriter;
        this.lifingCommentContent = lifingCommentContent;
        this.lifing = lifing;
    }

    /** 라이핑 댓글 수정 */
    public void updateComment(String lifingCommentContent) {
        this.lifingCommentContent = lifingCommentContent;
    }
}
