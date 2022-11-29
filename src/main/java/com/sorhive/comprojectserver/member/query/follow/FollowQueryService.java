package com.sorhive.comprojectserver.member.query.follow;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Class : IdPasswordNotMatchingException
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-31       부시연           최초 생성
 * 2022-11-20       부시연           팔로우 목록 조회 수정
 * 2022-11-20       부시연           팔로잉 목록 조회 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class FollowQueryService {

    private static final Logger log = LoggerFactory.getLogger(FollowQueryController.class);
    private final FollowMapper followMapper;
    private final TokenProvider tokenProvider;

    public FollowQueryService(FollowMapper followMapper, TokenProvider tokenProvider) {
        this.followMapper = followMapper;
        this.tokenProvider = tokenProvider;
    }

    /** 팔로워 목록 조회하기 */
    public FollowerListResponseDto findFollowerList(Long memberCode) {

        log.info("[FollowQueryService] findFollowerList Start ================");

        List<FollowData> followData = followMapper.findByFollowerId(memberCode);

        FollowerListResponseDto followerListResponseDto = new FollowerListResponseDto(
                followData,
                followData.size()
        );

        log.info("[FollowQueryService] findFollowerList End ================");

        return followerListResponseDto;
    }

    /** 팔로잉 목록 조회하기 */
    public FollowingListResponseDto findFollowingList(Long memberCode) {

        log.info("[FollowQueryService] findFollowingList Start ================");

        List<FollowData> followData = followMapper.findByFollowingId(memberCode);

        FollowingListResponseDto followingListResponseDto = new FollowingListResponseDto(
                followData,
                followData.size()
        );

        log.info("[FollowQueryService] findFollowerList End ================");

        return followingListResponseDto;
    }
}
