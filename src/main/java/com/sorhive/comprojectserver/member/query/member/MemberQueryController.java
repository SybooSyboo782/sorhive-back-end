package com.sorhive.comprojectserver.member.query.member;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.member.query.recommend.RecommendInfraQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Class : MemberQueryController
 * Comment: 멤버 조회용 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * 2022-11-11       부시연           회원 목록 조회 추가
 * 2022-11-11       부시연           회원 검색 추가
 * 2022-11-12       부시연           회원 검색 수정
 * 2022-11-13       부시연           하이브 전용 자신을 포함한 랜덤 회원 7명 조회 기능 추가
 * 2022-11-13       부시연           자신을 제외한 회원 목록 조회 추가
 * 2022-11-13       부시연           룸인 전용 자신과 룸인한 상대를 포함한 회원 7명 조회 기능 추가
 * 2022-11-14       부시연           마이페이지 조회 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class MemberQueryController {

    private static final Logger log = LoggerFactory.getLogger(MemberQueryController.class);
    private final MemberQueryService memberQueryService;
    private final RecommendInfraQueryService recommendInfraQueryService;

    public MemberQueryController(MemberQueryService memberQueryService, RecommendInfraQueryService recommendInfraQueryService) {
        this.memberQueryService = memberQueryService;
        this.recommendInfraQueryService = recommendInfraQueryService;
    }

    /** 회원 아이디 검색 */
    @GetMapping("member/search/{memberId}")
    public ResponseEntity<ResponseDto> findMemberByMemberId(@RequestHeader String Authorization, @PathVariable String memberId) {

        log.info("[MemberQueryController] findMemberByMemberId Start =================");
        log.info("[MemberQueryController] memberId : " + memberId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 ID 검색 성공", memberQueryService.findMemberByMemberId(accessToken, memberId)));
    }

    /** 전체 회원 목록 조회 */
    @GetMapping("member/list")
    public ResponseEntity<ResponseDto> findAllMember(@RequestHeader String Authorization) {

        log.info("[MemberQueryController] findAllMember Start =================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 회원 조회 성공", memberQueryService.findAllMember(accessToken)));
    }

    /** 자신이 팔로우 한 회원 목록 조회 */
    @GetMapping("member/list/{page}")
    public ResponseEntity<ResponseDto> findAllByMemberCode(@RequestHeader String Authorization, @PathVariable Long page) {

        log.info("[MemberQueryController] findAllByMemberCode Start =================");
        log.info("[MemberQueryController] page : " + page);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 목록 조회 성공", recommendInfraQueryService.findAllByMemberCode(accessToken, page)));
    }
    
    /** 룸인 할 때 회원 조회 */
    @GetMapping("member/roomin/{roomId}")
    public ResponseEntity<ResponseDto> findRoomInMember(@RequestHeader String Authorization,@PathVariable Long roomId) {

        log.info("[MemberQueryController] findRoomInMember Start =================");
        log.info("[MemberQueryController] roomId : " + roomId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "룸인 회원 조회 성공", memberQueryService.findRoomInMember(accessToken, roomId)));
    }

    /** 벌집 타기 할 때 랜덤 회원 조회 */
    @GetMapping("member/hive")
    public ResponseEntity<ResponseDto> findRandomMember(@RequestHeader String Authorization) {

        log.info("[MemberQueryController] findRandomMember Start =================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 랜덤 조회 성공", memberQueryService.findRandomMember(accessToken)));
    }

    /** 마이페이지 조회 */
    @GetMapping("mypage")
    public ResponseEntity<ResponseDto> selectMypage(@RequestHeader String Authorization) {

        log.info("[MemberQueryController] selectMypage Start =================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "마이페이지 조회 성공", memberQueryService.selectMypage(accessToken)));
    }

    /** 회원 상세 조회 */
    @GetMapping("member/{memberCode}")
    public ResponseEntity<ResponseDto> selectMemberByMemberCode(@RequestHeader String Authorization, @PathVariable Long memberCode) {

        log.info("[MemberQueryController] selectMemberByMemberCode Start =================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 상세 조회 성공", memberQueryService.selectMemberByMemberCode(accessToken, memberCode)));
    }

}
