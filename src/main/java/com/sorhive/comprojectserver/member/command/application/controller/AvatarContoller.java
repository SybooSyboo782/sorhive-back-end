package com.sorhive.comprojectserver.member.command.application.controller;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.member.command.application.dto.AvatarImageDto;
import com.sorhive.comprojectserver.member.command.application.dto.AvatarRequestDto;
import com.sorhive.comprojectserver.member.command.infra.AvatarImageInfraService;
import com.sorhive.comprojectserver.member.command.infra.AvatarInfraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 * Class : AvatarContoller
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-11       부시연           아바타 이미지 추가
 * 2022-11-11       부시연           아바타 생성
 * 2022-11-22       부시연           아바타 수정 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@RestController
@RequestMapping("api/v1/avatar")
public class AvatarContoller {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private AvatarInfraService avatarInfraService;

    private AvatarImageInfraService avatarImageInfraService;

    public AvatarContoller(AvatarInfraService avatarInfraService, AvatarImageInfraService avatarImageInfraService) {
        this.avatarInfraService = avatarInfraService;
        this.avatarImageInfraService = avatarImageInfraService;
    }

    /** 아바타 이미지 추가 */
    @PostMapping(value = "image")
    public ResponseEntity<ResponseDto> insertAvatarImage(@RequestHeader String Authorization, @RequestBody AvatarImageDto avatarImageDto) {

        log.info("[AvatarContoller] insertAvatarImage Start ====================");
        log.info("[AvatarContoller] avatarImageDto : " + avatarImageDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "아바타 이미지 생성 성공", avatarImageInfraService.insertAvatarImage(accessToken, avatarImageDto)));

    }

    /** 아바타 생성 */
    @PostMapping(value = "create")
    public ResponseEntity<ResponseDto> createAvatar(@RequestHeader String Authorization, @Valid @RequestBody AvatarRequestDto avatarRequestDto) {

        log.info("[AvatarContoller] createAvatar Start ====================");
        log.info("[AvatarContoller] avatarCreateDto : " + avatarRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "아바타 생성 성공", avatarInfraService.createAvatar(accessToken, avatarRequestDto)));

    }

    /** 아바타 수정 */
    @PutMapping(value = "")
    public ResponseEntity<ResponseDto> updateAvatar(@RequestHeader String Authorization, @Valid @RequestBody AvatarRequestDto avatarRequestDto) {

        log.info("[AvatarContoller] updateAvatar Start ====================");
        log.info("[AvatarContoller] AvatarRequestDto : " + avatarRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "아바타 수정 성공", avatarInfraService.updateAvatar(accessToken, avatarRequestDto)));

    }
}
