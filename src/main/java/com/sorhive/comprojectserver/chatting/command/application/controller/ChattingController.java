package com.sorhive.comprojectserver.chatting.command.application.controller;

import com.sorhive.comprojectserver.chatting.command.application.dto.ChattingCreateRequestDto;
import com.sorhive.comprojectserver.chatting.infra.ChattingInfraService;
import com.sorhive.comprojectserver.common.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * <pre>
 * Class : ChattingController
 * Comment: 채팅 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("api/v1")
public class ChattingController {
    private static final Logger log = LoggerFactory.getLogger(ChattingController.class);
    private final ChattingInfraService chattingInfraService;

    public ChattingController(ChattingInfraService chattingInfraService) {
        this.chattingInfraService = chattingInfraService;
    }

    /** 채팅 생성 */
    @PostMapping(value = "chatting")
    public ResponseEntity<ResponseDto> createChatting(@RequestHeader String Authorization, @Valid @RequestBody ChattingCreateRequestDto chattingCreateRequestDto) {

        log.info("[ChattingController] createChatting Start ===================");
        log.info("[ChattingController] chattingCreateDto : " + chattingCreateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "채팅 생성 성공", chattingInfraService.createChatting(accessToken, chattingCreateRequestDto)));
    }

}
