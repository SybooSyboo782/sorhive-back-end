package com.sorhive.comprojectserver.member.command.domain.model.follow;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : FollowerId
 * Comment: 팔로워 아이디 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-04       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Access(AccessType.FIELD)
public class FollowerId implements Serializable {

    @Column(name = "follower_id")
    private Long value;

    protected FollowerId() {
    }

    public FollowerId(Long value) { this.value = value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowerId followerId = (FollowerId) o;
        return Objects.equals(value, followerId.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static FollowerId of(Long value) { return new FollowerId(value); }
}
