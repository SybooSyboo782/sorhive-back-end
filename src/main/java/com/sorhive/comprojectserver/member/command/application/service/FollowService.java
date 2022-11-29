package com.sorhive.comprojectserver.member.command.application.service;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.domain.model.follow.Follow;
import com.sorhive.comprojectserver.member.command.domain.model.follow.FollowerId;
import com.sorhive.comprojectserver.member.command.domain.model.follow.FollowingId;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole;
import com.sorhive.comprojectserver.member.command.domain.repository.FollowRepository;
import com.sorhive.comprojectserver.member.command.exception.ExistFollowException;
import com.sorhive.comprojectserver.member.command.exception.NoFollowException;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import com.sorhive.comprojectserver.member.command.exception.SameMemberException;
import com.sorhive.comprojectserver.member.query.member.MemberDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
public class FollowService {

    private static final Logger log = LoggerFactory.getLogger(FollowService.class);
    private final FollowRepository followRepository;
    private final MemberDataDao memberDataDao;
    private final TokenProvider tokenProvider;

    public FollowService(FollowRepository followRepository, MemberDataDao memberDataDao, TokenProvider tokenProvider) {
        this.followRepository = followRepository;
        this.memberDataDao = memberDataDao;
        this.tokenProvider = tokenProvider;
    }

    /** 팔로우 추가 */
    @Transactional
    public Object createFollow(String accessToken, Long followingId) {
        log.info("[FollowService] createFollow Start ===============");
        log.info("[FollowService] followId : " + followingId);

        Long followerId = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(followerId == followingId) {
            throw new SameMemberException("동일한 회원입니다.");
        }

        if(memberDataDao.findByMemberCodeAndMemberRole(followingId, MemberRole.valueOf("ROLE_MEMBER")) == null) {
            throw new NoMemberException("해당 회원이 존재하지 않습니다.");
        }

        if(!followRepository.findByFollowingId(new FollowerId(followerId), new FollowingId(followingId)).isEmpty()) {
            throw new ExistFollowException("이미 팔로우를 하고 있습니다.");
        }

        Follow follow = new Follow(
                new FollowerId(followerId),
                new FollowingId(followingId)
        );

        followRepository.save(follow);

        return follow.getFollowId();

    }

    /** 팔로우 제거 */
    @Transactional
    public Object deleteFollow(Long followId) {
        log.info("[FollowService] deleteFollow Start ===============");
        log.info("[FollowService] followId : " + followId);

        if(followRepository.findByFollowIdAndDeleteYnEquals(followId, 'N') == null) {
            throw new NoFollowException("해당 팔로우 번호가 존재하지 않습니다.");
        }

        Optional<Follow> followingData = followRepository.findByFollowIdAndDeleteYnEquals(followId, 'N');
        Follow follow = followingData.get();
        follow.setDeleteYn('Y');
        followRepository.save(follow);

        return follow.getFollowId();

    }

}
