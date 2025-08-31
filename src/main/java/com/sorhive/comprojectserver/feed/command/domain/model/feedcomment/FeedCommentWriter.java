package com.sorhive.comprojectserver.feed.command.domain.model.feedcomment;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * <pre>
 * Class : FeedCommentWriter
 * Comment: 피드 댓글 작성자 도메인
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-05       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Getter
@AllArgsConstructor
public class FeedCommentWriter {

    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "feed_comment_writer_code"))
    )
    private MemberCode memberCode;

    @Column(name = "feed_comment_writer_name")
    private String name;

    @Column(name = "feed_comment_writer_id")
    private String id;


    protected FeedCommentWriter() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedCommentWriter that = (FeedCommentWriter) o;
        return Objects.equals(memberCode, that.memberCode) && Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, name, id);
    }
}
