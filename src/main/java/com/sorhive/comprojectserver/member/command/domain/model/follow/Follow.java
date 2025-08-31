package com.sorhive.comprojectserver.member.command.domain.model.follow;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : Follow
 * Comment: 팔로우 도메인 모델
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
@Entity
@Table(name = "tbl_follows")
public class Follow {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @Embedded
    private FollowerId followerId;

    @Embedded
    private FollowingId followingId;

    @Column(name = "follow_create_time")
    private Timestamp createTime;

    @Column(name = "follow_delete_time")
    private Timestamp deleteTime;

    @Column(name = "follow_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    public Follow(FollowerId followerId, FollowingId followingId) {

        this.followerId = followerId;
        this.followingId = followingId;
        this.deleteYn = 'N';
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public Follow() { }

    public Long getFollowId() { return followId; }

    public FollowerId getFollowerId() {
        return followerId;
    }

    public FollowingId getFollowingId() {
        return followingId;
    }

    public Character getDeleteYn() { return deleteYn; }

    public void setFollowerMember(FollowerId followerId) { this.followerId = followerId; }

    public void setFollowingMember(FollowingId followingId) { this.followingId = followingId; }

    public void setDeleteYn(Character deleteYn) {
        this.deleteYn = deleteYn;
        this.deleteTime = new Timestamp(System.currentTimeMillis());
    }
}
