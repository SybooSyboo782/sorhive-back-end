package com.sorhive.comprojectserver.member.query.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <pre>
 * Class : FollowData
 * Comment: 팔로우 데이터 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-10       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_follows")
@Getter
@NoArgsConstructor
public class FollowData {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "following_id")
    private Long followingId;

    @Column(name = "follow_delete_yn")
    private Character followDeleteYn;

}
