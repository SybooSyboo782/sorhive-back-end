package com.sorhive.comprojectserver.member.query.follow;

import lombok.Getter;

import java.util.List;

/**
 * <pre>
 * Class : FollowerResponseDto
 * Comment: 팔로워 응답 전송 객체
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
public class FollowerListResponseDto {

    private List<FollowData> followerData;
    private int followerCount;

    public FollowerListResponseDto(List<FollowData> followerData, int followerCount) {
        this.followerData = followerData;
        this.followerCount = followerCount;
    }
}
