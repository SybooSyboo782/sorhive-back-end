package com.sorhive.comprojectserver.member.query.follow;

import lombok.Getter;

import java.util.List;

/**
 * <pre>
 * Class : FollowingListResponseDto
 * Comment: 팔로잉 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FollowingListResponseDto {

    private List<FollowData> followerData;
    private int followingCount;

    public FollowingListResponseDto(List<FollowData> followData, int followingCount) {
        this.followerData = followData;
        this.followingCount = followingCount;
    }
}
