package com.sorhive.comprojectserver.member.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.query.member.MemberData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends SoftDeleteEntity {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private MemberData followerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private MemberData followingId;

    public Follow(MemberData followerId, MemberData followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
