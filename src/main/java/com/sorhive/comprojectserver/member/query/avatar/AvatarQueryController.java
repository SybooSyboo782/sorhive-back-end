package com.sorhive.comprojectserver.member.query.avatar;

import com.sorhive.comprojectserver.common.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Class : AvatarQueryController
 * Comment: 아바타 조회용 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@RestController
@RequestMapping("/api/v1")
public class AvatarQueryController {

    private static final Logger log = LoggerFactory.getLogger(AvatarQueryController.class);
    private final AvatarQueryService avatarQueryService;

    public AvatarQueryController(AvatarQueryService avatarQueryService) {
        this.avatarQueryService = avatarQueryService;
    }

    /** 아바타 조회 */
    @GetMapping("avatar")
    public ResponseEntity<ResponseDto> selectAvatar(@RequestHeader String Authorization) {

        log.info("[AvatarQueryController] selectAvatar Start ==========");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "아바타 조회 성공", avatarQueryService.selectAvatar(accessToken)));
    }
}
