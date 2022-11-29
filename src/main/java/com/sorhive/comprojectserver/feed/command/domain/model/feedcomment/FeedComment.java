package com.sorhive.comprojectserver.feed.command.domain.model.feedcomment;

import com.sorhive.comprojectserver.feed.command.domain.model.feed.Feed;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

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
public class FeedComment {

    @Id
    @Column(name="feed_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_comment_content")
    private String content;

    private FeedCommentWriter feedCommentWriter;

    @Column(name = "feed_comment_create_time")
    private Timestamp createTime;

    @Column(name = "feed_comment_upload_time")
    private Timestamp uploadTime;

    @Column(name = "feed_comment_delete_time")
    private Timestamp deleteTime;

    @Column(name = "feed_comment_delete_yn")
    @ColumnDefault(value = "'N'")
    private Character deleteYn;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    protected FeedComment() {}

    public FeedComment(FeedCommentWriter feedCommentWriter, String feedCommentContent, Feed feed) {
        this.feedCommentWriter = feedCommentWriter;
        this.content = feedCommentContent;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';
        this.feed = feed;
    }

    public void deleteFeedComment(Long feedCommentId) {

        this.id = feedCommentId;
        this.deleteYn = 'Y';
        this.deleteTime = new Timestamp(System.currentTimeMillis());

    }

    public void modifyFeedComment(Long feedCommentId, String feedCommentContent) {

        this.id = feedCommentId;
        this.content = feedCommentContent;
        this.uploadTime = new Timestamp(System.currentTimeMillis());

    }

    public void delete() {
        this.deleteYn = 'Y';
        this.deleteTime = new Timestamp(System.currentTimeMillis());
    }
}
