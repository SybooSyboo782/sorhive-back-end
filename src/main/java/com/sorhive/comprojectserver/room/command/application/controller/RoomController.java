package com.sorhive.comprojectserver.room.command.application.controller;

import com.sorhive.comprojectserver.common.ResponseDto;
import com.sorhive.comprojectserver.room.command.application.dto.FurnitureImageCreateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookCreateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookUpdateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.RoomCreateDto;
import com.sorhive.comprojectserver.room.command.application.service.RoomService;
import com.sorhive.comprojectserver.room.command.infra.RoomInfraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 * Class : RoomController
 * Comment: 방 컨트롤러
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-03       부시연           최초 생성
 * 2022-11-09       부시연           방 생성 기능 추가
 * 2022-11-10       부시연           접속용 방이미지 생성 기능 추가
 * 2022-11-13       부시연           방명록 생성 기능 추가
 * 2022-11-13       부시연           서비스, 인프라 서비스 분리
 * 2022-11-16       부시연           방명록 수정 기능 추가
 * 2022-11-16       부시연           방명록 삭제 기능 추가
 * 2022-11-17       부시연           가구 이미지 생성 기능 추가
 * 2022-11-17       부시연           가구 이미지 삭제 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@RestController
@RequestMapping("api/v1")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;
    private final RoomInfraService roomInfraService;

    public RoomController(RoomService roomService, RoomInfraService roomInfraService) {
        this.roomService = roomService;
        this.roomInfraService = roomInfraService;
    }

    /** 방 생성 */
    @PostMapping(value = "room")
    public ResponseEntity<ResponseDto> createRoom(@RequestHeader String Authorization, @Valid @RequestBody RoomCreateDto roomCreateDto) {

        log.info("[RoomController] createRoom Start ===================");
        log.info("[RoomController] roomCreateDto : " + roomCreateDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "방 생성 성공", roomInfraService.createRoom(accessToken, roomCreateDto)));
    }

    /** 가구 이미지 생성 */
    @PostMapping(value = "room/image")
    public ResponseEntity<ResponseDto> createFurnitureImage(@RequestHeader String Authorization, @Valid @RequestBody FurnitureImageCreateRequestDto furnitureImageCreateRequestDto) {

        log.info("[RoomController] createFurnitureImage Start ===================");
        log.info("[RoomController] furnitureImageCreateDto : " + furnitureImageCreateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "가구 이미지 생성 성공", roomInfraService.createFurnitureImage(accessToken, furnitureImageCreateRequestDto)));
    }

    /** 가구 이미지 제거 */
    @DeleteMapping(value = "room/image/{furnitureImageId}")
    public ResponseEntity<ResponseDto> deleteFurnitureImage(@RequestHeader String Authorization, @PathVariable Long furnitureImageId) {

        log.info("[RoomController] deleteFurnitureImage Start ===================");
        log.info("[RoomController] furnitureImageId : " + furnitureImageId);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "가구 이미지 제거 성공", roomService.deleteFurnitureImage(accessToken, furnitureImageId)));
    }

    /** 방명록 생성 */
    @PostMapping(value = "guestbook")
    public ResponseEntity<ResponseDto> createGuestBook(@RequestHeader String Authorization, @Valid @RequestBody GuestBookCreateRequestDto guestBookCreateRequestDto) {

        log.info("[RoomController] createGuestBook Start ===================");
        log.info("[RoomController] GuestBookCreateRequestDto : " + guestBookCreateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "방명록 생성 성공", roomService.createGuestBook(accessToken, guestBookCreateRequestDto)));
    }

    /** 방명록 수정 */
    @PutMapping(value = "guestbook")
    public ResponseEntity<ResponseDto> updateGuestBook(@RequestHeader String Authorization, @Valid @RequestBody GuestBookUpdateRequestDto guestBookUpdateRequestDto) {

        log.info("[RoomController] updateGuestBook Start ===================");
        log.info("[RoomController] guestBookRequestDto : " + guestBookUpdateRequestDto);

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "방명록 수정 성공", roomService.updateGuestBook(accessToken, guestBookUpdateRequestDto)));
    }

    /** 방명록 삭제 */
    @DeleteMapping(value = "guestbook/{guestbookId}")
    public ResponseEntity<ResponseDto> deleteGuestBook(@RequestHeader String Authorization, @PathVariable("guestbookId") Long guestBookId) {

        log.info("[RoomController] deleteGuestBook Start ===================");

        String accessToken = Authorization.substring(7);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.NO_CONTENT, "방명록 삭제 성공", roomService.deleteGuestBook(accessToken, guestBookId)));
    }
}
