package com.sorhive.comprojectserver.member.query.follow;

import lombok.Getter;

/**
 * <pre>
 * Class : FollowSummary
 * Comment: 팔로우 요약 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FollowSummary {
    
    private Long followId;
    private Long followingId;
    private Long followerId;
    protected FollowSummary() {}

    public FollowSummary(Long followId, Long followingId, Long followerId) {
        this.followId = followId;
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
