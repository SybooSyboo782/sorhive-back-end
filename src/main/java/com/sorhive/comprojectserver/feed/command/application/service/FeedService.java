package com.sorhive.comprojectserver.feed.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.feed.command.application.dto.*;
import com.sorhive.comprojectserver.feed.command.domain.model.Feed;
import com.sorhive.comprojectserver.feed.command.domain.model.FeedComment;
import com.sorhive.comprojectserver.feed.command.domain.model.FeedHoney;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedCommentRepository;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedHoneyRepository;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedRepository;
import com.sorhive.comprojectserver.feed.exception.NoFeedCommentException;
import com.sorhive.comprojectserver.feed.exception.NoFeedException;
import com.sorhive.comprojectserver.lifing.command.application.exception.AlreadyHoneyException;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Class : FeedService
 * Comment: 피드 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-12       부시연           피드 댓글 추가 기능 추가
 * 2022-11-16       부시연           피드 허니 추가 기능 추가
 * 2022-11-16       부시연           피드 허니 제거 기능 추가
 * 2022-11-19       부시연           피드 댓글 삭제 기능 추가
 * 2022-11-19       부시연           피드 댓글 수정 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
@Slf4j
@AllArgsConstructor
public class FeedService {
    private final TokenProvider tokenProvider;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedHoneyRepository feedHoneyRepository;
    private final MemberRepository memberRepository;

    /** 피드 댓글 작성 */
    @Transactional
    public FeedCommentCreateResponseDto createFeedComment(String accessToken, Long feedId, FeedCommentCreateRequestDto feedCommentCreateRequestDto) {

        log.info("[FeedService] createFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentCreateDto : " + feedCommentCreateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        Member feedCommentWriter = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        String feedCommentContent = feedCommentCreateRequestDto.getFeedCommentContent();

        Feed feed = feedRepository.findByFeedId(feedId);

        if (feed == null) {
            throw new NoFeedException();
        }

        FeedComment feedComment = new FeedComment(
                feedCommentContent,
                feedCommentWriter,
                feed
        );

        feedCommentRepository.save(feedComment);

        return new FeedCommentCreateResponseDto(
                feedComment.getId(),
                feedComment.getContent(),
                feedComment.getCreatedDate()
        );

    }

    /** 피드 댓글 수정 */
    public Object modifyFeedComment(String accessToken, Long feedCommentId, FeedCommentModifyRequestDto feedCommentModifyRequestDto) {

        log.info("[FeedService] modifyFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentId : " + feedCommentId);
        log.info("[FeedService] feedCommentModifyRequestDto : " + feedCommentModifyRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        FeedComment feedComment = feedCommentRepository.findByIdAndDeletedEquals(feedCommentId, false)
                .orElseThrow(() -> {
                    log.warn("[FeedService] NoFeedCommentException");
                    return new NoFeedCommentException("해당 피드 댓글은 존재하지 않거나 이미 삭제되었습니다.");
                });

        if (!feedComment.getFeedCommentWriter().getMemberCode().equals(memberCode)) {
            log.warn("[FeedService] NotSameWriterException");
            throw new NotSameWriterException("해당 피드 댓글의 작성자가 아닙니다.");
        }

        feedComment.modifyFeedComment(feedCommentId, feedCommentModifyRequestDto.getFeedCommentContent());
        feedCommentRepository.save(feedComment);

        return new FeedCommentModifyResponseDto(
                feedComment.getId(),
                feedComment.getContent(),
                feedComment.getUpdateDate()
        );

    }

    /** 피드 댓글 삭제 */
    @Transactional
    public Long deleteFeedComment(String accessToken, Long feedCommentId) {

        log.info("[FeedService] deleteFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentId : " + feedCommentId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        FeedComment feedComment = feedCommentRepository.findByIdAndDeletedEquals(feedCommentId, false)
                .orElseThrow(() -> {
                    log.warn("[FeedService] NoFeedCommentException");
                    return new NoFeedCommentException("해당 피드 댓글은 존재하지 않거나 이미 삭제되었습니다.");
                });

        if (!feedComment.getFeedCommentWriter().getMemberCode().equals(memberCode)) {
            log.warn("[FeedService] NotSameWriterException");
            throw new NotSameWriterException("해당 피드 댓글의 작성자가 아닙니다.");
        }

        feedComment.softDelete();
        feedCommentRepository.save(feedComment);

        return feedComment.getId();

    }

    /** 허니 추가 */
    @Transactional
    public FeedHoneyResponseCreateDto createFeedHoney(String accessToken, Long feedId) {

        log.info("[FeedService] createFeedHoney Start =========================================================");
        log.info("[FeedService] feedId : " + feedId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));
        
        Feed feed = feedRepository.findByFeedIdAndDeletedEquals(feedId, false)
                .orElseThrow(() -> new NoFeedException("해당 피드가 존재하지 않습니다. " + feedId));

        feedHoneyRepository.findByMemberMemberCodeAndFeedFeedIdAndDeletedEquals(memberCode, feedId, false)
                .ifPresent(h -> {
                    throw new AlreadyHoneyException("이미 허니를 했습니다.");
                });

        feed.countingFeedHoney(1);
        feedRepository.save(feed);

        Member member = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        FeedHoney feedHoney = new FeedHoney(
                feed,
                member
        );

        feedHoneyRepository.save(feedHoney);

        return new FeedHoneyResponseCreateDto(
                feedHoney.getId(),
                feedHoney.getFeed().getFeedId(),
                feedHoney.getMember().getMemberCode(),
                feedHoney.getCreatedDate()
        );

    }

    /** 허니 취소 */
    @Transactional
    public FeedHoneyResponseDeleteDto deleteFeedHoney(String accessToken, Long feedId) {

        log.info("[FeedService] deleteFeedHoney Start =========================================================");
        log.info("[FeedService] feedId : " + feedId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));


        Feed feed = feedRepository.findByFeedIdAndDeletedEquals(feedId, false)
                .orElseThrow(() -> new NoFeedException("해당 피드가 존재하지 않습니다. " + feedId));

        feed.countingFeedHoney(-1);
        feedRepository.save(feed);

        FeedHoney feedHoney = feedHoneyRepository.findByMemberMemberCodeAndFeedFeedIdAndDeletedEquals(memberCode, feedId, false)
                .orElseThrow(() -> new AlreadyDeleteException("이미 피드 허니가 취소 되어있습니다"));

        feedHoney.softDelete();
        feedHoneyRepository.save(feedHoney);

        return new FeedHoneyResponseDeleteDto(
                feedHoney.getId(),
                feedHoney.getFeed().getFeedId(),
                feedHoney.getMember().getMemberCode(),
                feedHoney.getDeletedAt()
        );
    }
}
