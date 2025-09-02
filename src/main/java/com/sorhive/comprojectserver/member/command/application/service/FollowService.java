package com.sorhive.comprojectserver.member.command.application.service;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.domain.model.Follow;
import com.sorhive.comprojectserver.member.command.domain.repository.FollowRepository;
import com.sorhive.comprojectserver.member.command.exception.NoFollowException;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import com.sorhive.comprojectserver.member.command.exception.SameMemberException;
import com.sorhive.comprojectserver.member.query.member.MemberData;
import com.sorhive.comprojectserver.member.query.member.MemberDataDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole.ROLE_MEMBER;

/**
 * <pre>
 * Class : FollowService
 * Comment: 팔로우 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-10       부시연           최초 생성
 * 2022-11-10       부시연           팔로우 추가
 * 2022-11-10       부시연           팔로우 삭제
 * 2022-11-12       부시연           동일 인물일 때 팔로우 하는 버그 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)\
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberDataDao memberDataDao;
    private final TokenProvider tokenProvider;

    /** 팔로우 추가 */
    @Transactional
    public Object createFollow(String accessToken, Long followingId) {
        log.info("[FollowService] createFollow Start ===============");
        log.info("[FollowService] followId : " + followingId);

        Long followerId = Long.valueOf(tokenProvider.getUserCode(accessToken));


        MemberData follower = memberDataDao.findByMemberCodeAndMemberRole(followerId, ROLE_MEMBER)
                .orElseThrow(() -> new NoMemberException("해당 회원이 존재하지 않습니다: " + followerId));

        MemberData following = memberDataDao.findByMemberCodeAndMemberRole(followingId, ROLE_MEMBER)
                .orElseThrow(() -> new NoMemberException("해당 회원이 존재하지 않습니다: " + followingId));

        if (follower.equals(following)) {
            throw new SameMemberException("동일한 회원입니다.");
        }

        Follow follow = new Follow(
                follower,
                following
        );

        followRepository.save(follow);

        return follow.getFollowId();
    }

    /** 팔로우 제거 */
    @Transactional
    public Object deleteFollow(Long followId) {
        log.info("[FollowService] deleteFollow Start ===============");
        log.info("[FollowService] followId : " + followId);

        Follow follow = followRepository.findByFollowIdAndDeletedEquals(followId, false)
                .orElseThrow(() -> new NoFollowException("해당 팔로우 번호가 존재하지 않습니다."));

        follow.softDelete();
        followRepository.save(follow);

        return follow.getFollowId();

    }

}
