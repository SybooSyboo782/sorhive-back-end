package com.sorhive.comprojectserver.feed.query;

import com.sorhive.comprojectserver.feed.exception.NoFeedException;
import com.sorhive.comprojectserver.feed.query.dto.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Class : FeedQueryService
 * Comment: 피드 조회용 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-04       부시연           최초 생성
 * 2022-11-14       부시연           피드 전체 조회에 총 피드 수 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
@AllArgsConstructor
public class FeedQueryService {

    private FeedMapper feedMapper;
    private static final Logger log = LoggerFactory.getLogger(FeedQueryService.class);

    /** 피드 전체 조회 */
    public SelectAllFeedResponseDto selectAllFeed(FeedRequestDto feedRequestDto) {

        log.info("[FeedQueryService] selectAllFeed Start ===============");
        log.info("[FeedQueryService] feedRequestDto : " + feedRequestDto);

        Long memberCode = feedRequestDto.getMemberCode();
        int pageNo = feedRequestDto.getPageNo() - 1;

        if(pageNo < 0) {
            pageNo = 0;
        }

        /* 피드가 없으면 예외처리 */
        if(feedMapper.selectAllFeed(memberCode, pageNo).isEmpty()) {
            throw new NoFeedException("피드가 없습니다.");
        }

        /* 전체 피드 응답용 전송 객체 만들기 */
        SelectAllFeedResponseDto selectAllFeedResponseDto = new SelectAllFeedResponseDto();

        /* 피드 목록 조회하기 */
        List<FeedSummary> feedSummaries = feedMapper.selectAllFeed(memberCode, pageNo);

        /* 응답 객체에 피드 목록 담기 */
        selectAllFeedResponseDto.setFeedSummary(feedSummaries);

        /* 해당 회원이 작성한 총 피드 목록 수 조회하기 */
        int feedCount = feedMapper.selectFeedCount(memberCode);

        /* 응답 객체에 총 피드 수 담기 */
        selectAllFeedResponseDto.setFeedCount(feedCount);

        return selectAllFeedResponseDto;
    }

    /** 피드 상세 조회 */
    public Object selectFeedDetail(Long feedId) {

        log.info("[FeedQueryService] selectFeedDetail Start ===============");
        log.info("[FeedQueryService] feedId : " + feedId);

        FeedResponseDto feedResponseDto = new FeedResponseDto();

        /* 피드 없을 경우 예외 처리 */
        if(feedMapper.selectFeedByFeedId(feedId) == null) {
            throw new NoFeedException();
        }

        feedResponseDto.setFeedSummary(feedMapper.selectFeedByFeedId(feedId));

        /* 피드에 이미지 있는지 확인 */
        if(feedMapper.selectAllFeedImages(feedId) != null) {

            log.info("[FeedQueryService] selectAllFeedImages Start ============" );

            List<FeedImageSummary> feedImageSummaryList = feedMapper.selectAllFeedImages(feedId);

            feedResponseDto.setFeedImageSummaryList(feedImageSummaryList);
        }

        /* 피드에 댓글 있는지 확인 */
        if(feedMapper.selectAllFeedComments(feedId) != null) {

            log.info("[FeedQueryService] selectAllFeedComments Start ============" );

            List<FeedCommentSummary> feedCommentSummaries = feedMapper.selectAllFeedComments(feedId);

            feedResponseDto.setFeedCommentSummaryList(feedCommentSummaries);
        }


        return feedResponseDto;
    }
}
