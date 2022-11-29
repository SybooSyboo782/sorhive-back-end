package com.sorhive.comprojectserver.member.query.follow;

import com.sorhive.comprojectserver.common.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Class : FollowQueryController
 * Comment: 팔로우 조회 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-10       부시연           최초 생성
 * 2022-11-20       부시연           팔로우 목록 조회 수정
 * 2022-11-20       부시연           팔로잉 목록 조회 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class FollowQueryController {

    private static final Logger log = LoggerFactory.getLogger(FollowQueryController.class);
    private final FollowQueryService followQueryService;

    public FollowQueryController(FollowQueryService followQueryService) {
        this.followQueryService = followQueryService;
    }

    /** 팔로워 목록 조회 */
    @GetMapping("follower/{memberCode}")
    public ResponseEntity<ResponseDto> findFollowerList(@PathVariable Long memberCode) {

        log.info("[FollowQueryController] findFollowerList Start ==================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "팔로워 목록 조회 성공", followQueryService.findFollowerList(memberCode)));
    }

    /** 팔로잉 목록 조회 */
    @GetMapping("following/{memberCode}")
    public ResponseEntity<ResponseDto> findFollowingList(@PathVariable Long memberCode) {

        log.info("[FollowQueryController] findFollowingList Start ==================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "팔로잉 목록 조회 성공", followQueryService.findFollowingList(memberCode)));
    }
}
