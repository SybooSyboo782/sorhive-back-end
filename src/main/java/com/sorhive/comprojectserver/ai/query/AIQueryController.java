package com.sorhive.comprojectserver.ai.query;

import com.sorhive.comprojectserver.common.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Class : AIQueryController
 * Comment: AI 조회용 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       부시연           최초 생성
 * 2022-11-27       부시연           AI 학습용 데이터 제공
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("ai")
public class AIQueryController {
    private static final Logger log = LoggerFactory.getLogger(AIQueryController.class);
    private final AIQueryService aiQueryService;

    public AIQueryController(AIQueryService aiQueryService) {
        this.aiQueryService = aiQueryService;
    }

    /** AI 학습용 데이터 제공 */
    @GetMapping("lifing")
    public ResponseEntity<ResponseDto> aiLearningData() {

        log.info("[AIQueryController] aiLearningData Start =================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "AI 학습 데이터 제공 성공", aiQueryService.aiLearningData()));
    }

}
