package com.sorhive.comprojectserver.member.query.member;

import com.sorhive.comprojectserver.member.query.follow.FollowSummary;
import lombok.Getter;

/**
 * <pre>
 * Class : MypageData
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * 2022-11-26       부시연           회원 상세 조회 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class MypageDto {

    private Long memberCode;
    private String memberName;
    private String memberId;
    private String memberRoomImage;
    private FollowSummary followingSummary;
    private FollowSummary followerSummary;
    private int followingCount;
    private int followerCount;
    private int feedCount;

    public MypageDto(String memberId, Long memberCode, String memberName, String roomImage, int countFeed, int countFollower, int countFollowing) {
        this.memberId = memberId;
        this.memberCode = memberCode;
        this.memberName = memberName;
        this.memberRoomImage = roomImage;
        this.feedCount = countFeed;
        this.followerCount = countFollower;
        this.followingCount = countFollowing;

    }

    public void addFollowingSummary(FollowSummary followingSummary) {
        this.followingSummary = followingSummary;
    }

    public void addFollowerSummary(FollowSummary followerSummary) {
        this.followerSummary = followerSummary;
    }
}
