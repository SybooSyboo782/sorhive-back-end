package com.sorhive.comprojectserver.feed.command.domain.model.feed;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : FeedId
 * Comment: 피드 아이디 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Access(AccessType.FIELD)
public class FeedId implements Serializable {

    @Column(name="feed_id")
    private Long value;

    protected FeedId() {
    }

    public FeedId(Long value) { this.value = value; }

    public Long getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedId feedId = (FeedId) o;
        return Objects.equals(value, feedId.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static FeedId of(Long value) { return new FeedId(value); }
}
