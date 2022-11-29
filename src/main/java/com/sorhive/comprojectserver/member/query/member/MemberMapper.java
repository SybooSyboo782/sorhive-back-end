package com.sorhive.comprojectserver.member.query.member;

import com.sorhive.comprojectserver.member.query.follow.FollowSummary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * Class : MemberMapper
 * Comment: 멤버 매퍼
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * 2022-11-13       부시연           자신을 포함한 전체 회원 조회
 * 2022-11-13       부시연           제일 높은 회원 코드값 조회
 * 2022-11-14       부시연           해당 회원이 작성한 총 피드 수 조회
 * 2022-11-14       부시연           해당 회원이 팔로우한 총 팔로워 수 조회
 * 2022-11-14       부시연           해당 회원을 팔로잉 한 총 팔로잉 수 조회
 * 2022-11-26       부시연           회원 상세 조회 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Mapper
public interface MemberMapper {
    MemberSummary findOneByMemberCode(Long memberCode);

    int findMaxMemberCode();

    List<MemberSummary> findAllFollowerByMemberCode(Long memberCode, long offset);

    MemberData findByMemberCode(Long memberCode);

    List<MemberData> findAllFollowerByFollowerCode(Long roomId);

    /* 해당 회원이 작성한 총 피드 수 */
    int countFeed(Long memberCode);

    /* 해당 회원이 팔로우한 총 팔로워 수 */
    int countFollower(Long memberCode);

    /* 해당 회원을 팔로잉 한 총 팔로잉 수 */
    int countFollowing(Long memberCode);

    FollowSummary findFolloingSummary(Long memberCode, Long ownMemberCode);

    FollowSummary findFollowerSummary(Long memberCode, Long ownMemberCode);
}
