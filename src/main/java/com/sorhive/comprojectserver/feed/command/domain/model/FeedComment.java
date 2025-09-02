package com.sorhive.comprojectserver.feed.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Class : FeedComment
 * Comment: 피드 댓글 도메인
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-19       부시연           피드 댓글 삭제 추가
 * 2022-11-19       부시연           피드 댓글 수정 추가
 * 2022-11-19       부시연           피드로 댓글 삭제 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_feed_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedComment extends SoftDeleteEntity {

    @Id
    @Column(name="feed_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_comment_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member feedCommentWriter;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public FeedComment(String feedCommentContent, Member feedCommentWriter, Feed feed) {
        this.content = feedCommentContent;
        this.feedCommentWriter = feedCommentWriter;
        this.feed = feed;
    }

    public void modifyFeedComment(Long feedCommentId, String feedCommentContent) {
        this.id = feedCommentId;
        this.content = feedCommentContent;
    }
}
