package com.sorhive.comprojectserver.chatting.query;

import com.sorhive.comprojectserver.common.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Class : ChattingQueryController
 * Comment: 채팅 조회 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-18       부시연           최초 생성
 * 2022-11-18       부시연           채팅 목록 조회
 * 2022-11-19       부시연           채팅 상세 조회
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class ChattingQueryController {
    
    private static final Logger log = LoggerFactory.getLogger(ChattingQueryController.class);
    private final ChattingQueryService chattingQueryService;

    public ChattingQueryController(ChattingQueryService chattingQueryService) {
        this.chattingQueryService = chattingQueryService;
    }
    
    @GetMapping("chatting")
    public ResponseEntity<ResponseDto> findChattingList(@RequestHeader String Authorization) {

        log.info("[ChattingQueryController] findChattingList Start ==========================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "채팅 목록 조회 성공", chattingQueryService.findChattingList(accessToken)));

    }

    @GetMapping("chatting/{memberCode}")
    public ResponseEntity<ResponseDto> findChattingDetail(@RequestHeader String Authorization, @PathVariable Long memberCode) {

        log.info("[ChattingQueryController] findChattingDetail Start ==========================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "채팅 상세 조회 성공", chattingQueryService.findChattingDetail(accessToken, memberCode)));

    }

}
