package com.sorhive.comprojectserver.lifing.query;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.lifing.query.dto.LifingRequestDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Class : LifingQueryController
 * Comment: 라이핑 조회용 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-12       부시연           회원 번호로 회원의 모든 라이핑 조회
 * 2022-11-12       부시연           라이핑 번호로 상세 조회
 * 2022-11-23       부시연           회원 상세 조회 기능 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class LifingQueryController {

    private static final Logger log = LoggerFactory.getLogger(LifingQueryController.class);
    private final LifingQueryService lifingQueryService;

    /** 방 번호로 모든 라이핑 조회하기 */
    @GetMapping("lifing")
    public ResponseEntity<ResponseDto> findAllLifingByMemberCode(@RequestBody LifingRequestDto lifingRequestDto) {

        log.info("[QueryLifingController] findAllLifingByMemberCode Start ==========");
        log.info("[QueryLifingController] lifingRequestDto : " + lifingRequestDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "라이핑 조회 성공", lifingQueryService.findAllLifingByMemberCode(lifingRequestDto)));
    }

    /** 라이핑 상세 조회 */
    @GetMapping("lifing/{writerCode}")
    public ResponseEntity<ResponseDto> findLifingByLifingId(@RequestHeader String Authorization, @PathVariable Long writerCode) {

        log.info("[QueryLifingController] findLifingByLifingId Start ==========");
        log.info("[QueryLifingController] writerCode : " + writerCode);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "라이핑 상세 조회 성공", lifingQueryService.findLifingByLifingId(accessToken, writerCode)));
    }

}
