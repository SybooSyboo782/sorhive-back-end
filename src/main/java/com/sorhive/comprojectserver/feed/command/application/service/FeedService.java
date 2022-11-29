package com.sorhive.comprojectserver.feed.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.feed.command.application.dto.*;
import com.sorhive.comprojectserver.feed.command.domain.model.feed.Feed;
import com.sorhive.comprojectserver.feed.command.domain.model.feed.FeedId;
import com.sorhive.comprojectserver.feed.command.domain.model.feedcomment.FeedComment;
import com.sorhive.comprojectserver.feed.command.domain.model.feedcomment.FeedCommentWriter;
import com.sorhive.comprojectserver.feed.command.domain.model.feedcomment.FeedCommentWriterService;
import com.sorhive.comprojectserver.feed.command.domain.model.feedhoney.FeedHoney;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedCommentRepository;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedHoneyRepository;
import com.sorhive.comprojectserver.feed.command.domain.repository.FeedRepository;
import com.sorhive.comprojectserver.feed.exception.NoFeedCommentException;
import com.sorhive.comprojectserver.feed.exception.NoFeedException;
import com.sorhive.comprojectserver.lifing.command.application.exception.AlreadyHoneyException;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
public class FeedService {
    private static final Logger log = LoggerFactory.getLogger(FeedService.class);
    private final TokenProvider tokenProvider;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedHoneyRepository feedHoneyRepository;
    private final FeedCommentWriterService feedCommentWriterService;

    public FeedService(TokenProvider tokenProvider, FeedRepository feedRepository, FeedCommentRepository feedCommentRepository, FeedHoneyRepository feedHoneyRepository, FeedHoneyRepository feedhoneyRepository, FeedHoneyRepository feedHoneyRepository1, FeedCommentWriterService feedCommentWriterService) {
        this.tokenProvider = tokenProvider;
        this.feedRepository = feedRepository;
        this.feedCommentRepository = feedCommentRepository;
        this.feedHoneyRepository = feedHoneyRepository1;
        this.feedCommentWriterService = feedCommentWriterService;
    }

    /** 피드 댓글 작성 */
    @Transactional
    public FeedCommentCreateResponseDto createFeedComment(String accessToken, Long feedId, FeedCommentCreateRequestDto feedCommentCreateRequestDto) {

        log.info("[FeedService] createFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentCreateDto : " + feedCommentCreateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        FeedCommentWriter feedCommentWriter = feedCommentWriterService.createFeedCommentWriter(new MemberCode(memberCode));

        String feedCommentContent = feedCommentCreateRequestDto.getFeedCommentContent();

        Feed feed = feedRepository.findByFeedId(feedId);

        if(feed == null) {
            throw new NoFeedException();
        }

        FeedComment feedComment = new FeedComment(
                feedCommentWriter,
                feedCommentContent,
                feed
        );

        feedCommentRepository.save(feedComment);

        FeedCommentCreateResponseDto feedCommentCreateResponseDto =
                new FeedCommentCreateResponseDto(
                        feedComment.getId(),
                        feedComment.getContent(),
                        feedComment.getCreateTime()
                );

        return feedCommentCreateResponseDto;

    }

    /** 피드 댓글 수정 */
    public Object modifyFeedComment(String accessToken, Long feedCommentId, FeedCommentModifyRequestDto feedCommentModifyRequestDto) {

        log.info("[FeedService] modifyFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentId : " + feedCommentId);
        log.info("[FeedService] feedCommentModifyRequestDto : " + feedCommentModifyRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(feedCommentRepository.findById(feedCommentId) == null) {

            log.warn("[FeedService] NoFeedCommentExecption");
            throw new NoFeedCommentException("해당 피드 댓글은 존재하지 않습니다.");

        }

        if(feedCommentRepository.findByIdAndDeleteYnEquals(feedCommentId, 'N').isEmpty()) {

            log.warn("[FeedService] NoFeedCommentExecption");
            throw new NoFeedCommentException("해당 피드 댓글은 이미 삭제 되었습니다.");

        }

        Optional<FeedComment> feedCommentData = feedCommentRepository.findByIdAndDeleteYnEquals(feedCommentId, 'N');

        if(feedCommentData.get().getFeedCommentWriter().getMemberCode().getValue() != memberCode) {

            log.warn("[FeedService] NotSameWriterException");
            throw new NotSameWriterException("해당 피드 댓글의 작성자가 아닙니다.");

        }

        FeedComment feedComment = feedCommentData.get();

        feedComment.modifyFeedComment(feedCommentId, feedCommentModifyRequestDto.getFeedCommentContent());

        feedCommentRepository.save(feedComment);

        FeedCommentModifyResponseDto feedCommentModifyResponseDto = new FeedCommentModifyResponseDto(
                feedComment.getId(),
                feedComment.getContent(),
                feedComment.getUploadTime()
        );

        return feedCommentModifyResponseDto;

    }

    /** 피드 댓글 삭제 */
    @Transactional
    public Long deleteFeedComment(String accessToken, Long feedCommentId) {

        log.info("[FeedService] deleteFeedComment Start =========================================================");
        log.info("[FeedService] feedCommentId : " + feedCommentId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(feedCommentRepository.findById(feedCommentId) == null) {

            log.warn("[FeedService] NoFeedCommentExecption");
            throw new NoFeedCommentException("해당 피드 댓글은 존재하지 않습니다.");

        }

        if(feedCommentRepository.findByIdAndDeleteYnEquals(feedCommentId, 'N').isEmpty()) {

            log.warn("[FeedService] NoFeedCommentExecption");
            throw new NoFeedCommentException("해당 피드 댓글은 이미 삭제 되었습니다.");

        }

        Optional<FeedComment> feedCommentData = feedCommentRepository.findByIdAndDeleteYnEquals(feedCommentId, 'N');

        if(feedCommentData.get().getFeedCommentWriter().getMemberCode().getValue() != memberCode) {

            log.warn("[FeedService] NotSameWriterException");
            throw new NotSameWriterException("해당 피드 댓글의 작성자가 아닙니다.");

        }

        FeedComment feedComment = feedCommentData.get();

        feedComment.deleteFeedComment(feedCommentId);

        feedCommentRepository.save(feedComment);

        return feedComment.getId();

    }

    /** 허니 추가 */
    @Transactional
    public FeedHoneyResponseCreateDto createFeedHoney(String accessToken, Long feedId) {

        log.info("[FeedService] createFeedHoney Start =========================================================");
        log.info("[FeedService] feedId : " + feedId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(feedRepository.findByFeedId(feedId) == null) {
            throw new NoFeedException("해당 피드가 존재하지 않습니다.");
        }

        Optional<Feed> feedData = feedRepository.findByFeedIdAndFeedDeleteYnEquals(feedId, 'N');

        if(!feedHoneyRepository.findByMemberCodeAndFeedIdAndDeleteYnEquals(new MemberCode(memberCode), new FeedId(feedId), 'N').isEmpty() ) {
            throw new AlreadyHoneyException("이미 허니를 했습니다.");
        }

        Feed feed = feedData.get();
        feed.countingFeedHoney(1);
        feedRepository.save(feed);

        FeedHoney feedHoney = new FeedHoney(
                new MemberCode(memberCode),
                new FeedId(feedId)
        );

        feedHoneyRepository.save(feedHoney);

        FeedHoneyResponseCreateDto feedHoneyResponseCreateDto = new FeedHoneyResponseCreateDto(
                feedHoney.getId(),
                feedHoney.getFeedId().getValue(),
                feedHoney.getMemberCode().getValue(),
                feedHoney.getCreateTime()
        );

        return feedHoneyResponseCreateDto;

    }

    /** 허니 취소 */
    @Transactional
    public FeedHoneyResponseDeleteDto deleteFeedHoney(String accessToken, Long feedId) {

        log.info("[FeedService] deleteFeedHoney Start =========================================================");
        log.info("[FeedService] feedId : " + feedId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(feedRepository.findByFeedId(feedId) == null) {
            throw new NoFeedException("해당 피드가 존재하지 않습니다.");
        }

        Optional<Feed> feedData = feedRepository.findByFeedIdAndFeedDeleteYnEquals(feedId, 'N');

        Feed feed = feedData.get();
        feed.countingFeedHoney(-1);
        feedRepository.save(feed);

        if(feedHoneyRepository.findByMemberCodeAndFeedIdAndDeleteYnEquals(new MemberCode(memberCode), new FeedId(feedId), 'N').isEmpty() ) {
            throw new AlreadyDeleteException("이미 피드 허니가 취소 되어있습니다");
        }

        Optional<FeedHoney> honeyData = feedHoneyRepository.findByMemberCodeAndFeedIdAndDeleteYnEquals(new MemberCode(memberCode), new FeedId(feedId), 'N');

        FeedHoney feedHoney = honeyData.get();


        feedHoney.setDeleteYn('Y');
        feedHoneyRepository.save(feedHoney);

        FeedHoneyResponseDeleteDto feedHoneyResponseDeleteDto = new FeedHoneyResponseDeleteDto(
                feedHoney.getId(),
                feedHoney.getFeedId().getValue(),
                feedHoney.getMemberCode().getValue(),
                feedHoney.getDeleteTime()
        );

        return feedHoneyResponseDeleteDto;

    }
}
