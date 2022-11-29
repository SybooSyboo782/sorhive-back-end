package com.sorhive.comprojectserver.feed.command.application.controller;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.feed.command.application.dto.*;
import com.sorhive.comprojectserver.feed.command.application.service.FeedService;
import com.sorhive.comprojectserver.feed.command.infra.FeedInfraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Class : FeedController
 * Comment: 피드 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-12       부시연           피드 생성 추가
 * 2022-11-12       부시연           피드 댓글 생성 추가
 * 2022-11-16       부시연           피드 허니 추가 기능 추가
 * 2022-11-16       부시연           피드 허니 제거 기능 추가
 * 2022-11-19       부시연           피드 댓글 제거 기능 추가
 * 2022-11-19       부시연           피드 댓글 수정 기능 추가
 * 2022-11-19       부시연           피드 삭제 기능 추가
 * 2022-11-20       부시연           피드 수정 기능 추가
 * 2022-11-20       부시연           피드 이미지 생성 기능 추가
 * 2022-11-20       부시연           피드 이미지 삭제 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class FeedController {

    private static final Logger log = LoggerFactory.getLogger(FeedController.class);
    private final FeedInfraService feedInfraService;
    private final FeedService feedService;

    public FeedController(FeedInfraService feedInfraService, FeedService feedService) {
        this.feedInfraService = feedInfraService;
        this.feedService = feedService;
    }

    /** 피드 작성 */
    @PostMapping("feed")
    public ResponseEntity<ResponseDto> createFeed(@RequestHeader String Authorization, @RequestBody FeedCreateRequestDto feedCreateRequestDto) {

        log.info("[FeedController] createFeed Start ============================");
        log.info("[feedCreateDto] " + feedCreateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 생성 성공", feedInfraService.createFeed(accessToken, feedCreateRequestDto)));

    }

    /** 피드 수정 */
    @PutMapping("feed")
    public ResponseEntity<ResponseDto> modifyFeed(@RequestHeader String Authorization, @RequestBody FeedModifyRequestDto feedModifyRequestDto) {

        log.info("[FeedController] modifyFeed Start ============================");
        log.info("[feedModifyRequestDto] " + feedModifyRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 수정 성공", feedInfraService.modifyFeed(accessToken, feedModifyRequestDto)));

    }

    /** 피드 삭제 */
    @DeleteMapping("feed/{feedId}")
    public ResponseEntity<ResponseDto> deleteFeed(@RequestHeader String Authorization, @PathVariable Long feedId) {

        log.info("[FeedController] deleteFeed Start ============================");
        log.info("[feedId] " + feedId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "피드 삭제 성공", feedInfraService.deleteFeed(accessToken, feedId)));

    }

    /** 피드 이미지 작성 */
    @PostMapping("feed/image")
    public ResponseEntity<ResponseDto> createFeedImage(@RequestHeader String Authorization, @RequestBody FeedImageCreateRequestDto feedImageCreateRequestDto) {

        log.info("[FeedController] createFeedImage Start ============================");
        log.info("[feedImageCreateRequestDto] " + feedImageCreateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 이미지 생성 성공", feedInfraService.createFeedImage(accessToken, feedImageCreateRequestDto)));

    }

    /** 피드 이미지 삭제 */
    @DeleteMapping("feed/image/{feedImageId}")
    public ResponseEntity<ResponseDto> deleteFeedImage(@RequestHeader String Authorization, @PathVariable Long feedImageId ) {

        log.info("[FeedController] deleteFeedImage Start ============================");
        log.info("[feedImageId] " + feedImageId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 이미지 삭제 성공", feedInfraService.deleteFeedImage(accessToken, feedImageId)));

    }


    /** 피드 댓글 작성 */
    @PostMapping("feed/comment/{feedId}")
    public ResponseEntity<ResponseDto> createFeedComment(@RequestHeader String Authorization, @PathVariable Long feedId, @RequestBody FeedCommentCreateRequestDto feedCommentCreateRequestDto) {

        log.info("[FeedController] createFeedComment Start ============================");
        log.info("[feedId] " + feedId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 댓글 추가 성공", feedService.createFeedComment(accessToken, feedId, feedCommentCreateRequestDto)));

    }

    /** 피드 댓글 수정 */
    @PutMapping("feed/comment")
    public ResponseEntity<ResponseDto> modifyFeedComment(@RequestHeader String Authorization, @PathVariable Long feedCommentId, @RequestBody FeedCommentModifyRequestDto feedCommentModifyRequestDto) {

        log.info("[FeedController] modifyFeedComment Start ============================");
        log.info("[feedCommentModifyRequestDto] " + feedCommentModifyRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 댓글 수정 성공", feedService.modifyFeedComment(accessToken, feedCommentId, feedCommentModifyRequestDto)));

    }

    /** 피드 댓글 삭제 */
    @DeleteMapping("feed/comment/{feedCommentId}")
    public ResponseEntity<ResponseDto> deleteFeedComment(@RequestHeader String Authorization, @PathVariable Long feedCommentId) {

        log.info("[FeedController] deleteFeedComment Start ============================");
        log.info("[feedCommentId] " + feedCommentId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "피드 댓글 삭제 성공", feedService.deleteFeedComment(accessToken, feedCommentId)));

    }

    /** 허니 생성 */
    @PostMapping("feed/honey/{feedId}")
    public ResponseEntity<ResponseDto> createFeedHoney(@RequestHeader String Authorization, @PathVariable Long feedId) {

        log.info("[FeedController] createFeedHoney Start ============================");
        log.info("[feedId] " + feedId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "피드 허니 추가 성공", feedService.createFeedHoney(accessToken, feedId)));

    }

    /** 허니 제거 */
    @DeleteMapping("feed/honey/{feedId}")
    public ResponseEntity<ResponseDto> deleteFeedHoney(@RequestHeader String Authorization, @PathVariable Long feedId) {

        log.info("[FeedController] deleteFeedHoney Start ============================");
        log.info("[feedId] " + feedId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "피드 허니 삭제 성공", feedService.deleteFeedHoney(accessToken, feedId)));

    }

}
