package com.sorhive.comprojectserver.room.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookCreateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookResponseDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookUpdateRequestDto;
import com.sorhive.comprojectserver.room.command.domain.model.FurnitureImage;
import com.sorhive.comprojectserver.room.command.domain.model.GuestBook;
import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import com.sorhive.comprojectserver.room.command.domain.repository.FurnitureImageRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.GuestBookRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.RoomRepository;
import com.sorhive.comprojectserver.room.execption.NoFurnitureImageException;
import com.sorhive.comprojectserver.room.execption.NoGuestBookException;
import com.sorhive.comprojectserver.room.execption.NoRoomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Class : RoomService
 * Comment: 방 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * 2022-11-13       부시연           방명록 생성 추가
 * 2022-11-16       부시연           방명록 수정 추가
 * 2022-11-16       부시연           방명록 삭제 추가
 * 2022-11-20       부시연           가구 이미지 삭제 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final GuestBookRepository guestBookRepository;
    private final FurnitureImageRepository furnitureImageRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    /** 방명록 생성 */
    @Transactional
    public GuestBookResponseDto createGuestBook(String accessToken, GuestBookCreateRequestDto guestBookCreateRequestDto) {

        log.info("[RoomService] createRoom Start ===================================");
        log.info("[RoomService] roomCreateDto {}", guestBookCreateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 방명록 작성자 생성 */
        Member guestBookWriter = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        /* 요청 객체에서 값 꺼내오기 */
        String guestBookContent = guestBookCreateRequestDto.getGuestBookContent();
        Long roomId = guestBookCreateRequestDto.getRoomId();

        /* 방번호에 맞는 방 찾기 */
        Room room = roomRepository.findById(roomId);

        if (room == null) {
            throw new NoRoomException("해당 방은 존재하지 않습니다.");
        }

        /* 방명록 생성하기 */
        GuestBook guestBook = new GuestBook(
                guestBookContent,
                guestBookWriter,
                room
        );

        /* 방명록 저장하기 */
        guestBookRepository.save(guestBook);

        /* 방명록 응답 전송 객체 생성 */
        GuestBookResponseDto guestBookResponseDto = new GuestBookResponseDto(
                guestBook.getId(),
                guestBook.getContent(),
                guestBook.getGuestBookWriter().getMemberCode(),
                guestBook.getGuestBookWriter().getMemberName(),
                guestBook.getGuestBookWriter().getMemberId(),
                guestBook.getRoom().getId(),
                guestBook.getCreatedDate()
        );

        log.info("[RoomService] guestBookCreateResponseDto {}", guestBookResponseDto);
        log.info("[RoomService] createRoom End ===================================");

        return guestBookResponseDto;
    }

    /** 방명록 수정 */
    @Transactional
    public GuestBookResponseDto updateGuestBook(String accessToken, GuestBookUpdateRequestDto guestBookUpdateRequestDto) {

        log.info("[RoomService] updateGuestBook Start ===================================");
        log.info("[RoomService] guestBookUpdateRequestDto {}", guestBookUpdateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 요청 객체에서 값 꺼내오기 */
        String guestBookContent = guestBookUpdateRequestDto.getContent();
        Long guestBookId = guestBookUpdateRequestDto.getGuestBookId();

        GuestBook guestBook = guestBookRepository.findById(guestBookId)
                .orElseThrow(() -> new NoGuestBookException("해당 방명록은 존재하지 않습니다.: " + guestBookId));

        if (guestBook.isDeleted()) {
            throw new AlreadyDeleteException("해당 방명록은 이미 삭제 됐습니다.");
        }

        /* 방번호에 맞는 방 찾기 */

        if (!guestBook.getGuestBookWriter().getMemberCode().equals(memberCode)) {
            throw new NotSameWriterException("방명록 작성자와 요청자가 다릅니다");
        }

        guestBook.updateGuestBook(guestBookContent);

        /* 방명록 저장하기 */
        guestBookRepository.save(guestBook);

        /* 방명록 응답 전송 객체 생성 */
        GuestBookResponseDto guestBookResponseDto = new GuestBookResponseDto(
                guestBook.getId(),
                guestBook.getContent(),
                guestBook.getGuestBookWriter().getMemberCode(),
                guestBook.getGuestBookWriter().getMemberName(),
                guestBook.getGuestBookWriter().getMemberId(),
                guestBook.getRoom().getId(),
                guestBook.getCreatedDate()
        );

        log.info("[RoomService] guestBookUpdateRequestDto {}", guestBookResponseDto);
        log.info("[RoomService] updateGuestBook End ===================================");

        return guestBookResponseDto;
    }

    /** 방명록 삭제 */
    @Transactional
    public Object deleteGuestBook(String accessToken, Long guestBookId) {

        log.info("[RoomService] deleteGuestBook Start ===================================");
        log.info("[RoomService] guestBookId ", guestBookId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        GuestBook guestBook = guestBookRepository.findById(guestBookId)
                .orElseThrow(() -> new NoGuestBookException("해당 방명록은 존재하지 않습니다."));

        if (guestBook.isDeleted()) {
            throw new AlreadyDeleteException("해당 방명록은 이미 삭제 됐습니다.");
        }

        Long guestBookWriterCode = guestBook.getGuestBookWriter().getMemberCode();

        if (!guestBookWriterCode.equals(memberCode)) {
            throw new NotSameWriterException("방명록 작성자와 요청자가 다릅니다");
        }

        guestBook.softDelete();
        guestBookRepository.save(guestBook);

        return guestBook.getId();

    }

    /** 가구 이미지 제거 */
    @Transactional
    public Object deleteFurnitureImage(String accessToken, Long furnitureImageId) {

        log.info("[RoomService] deleteFurnitureImage Start ===================================");
        log.info("[RoomService] furnitureImageId ", furnitureImageId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if (furnitureImageRepository.findById(furnitureImageId) == null) {
            throw new NoFurnitureImageException("해당 가구 이미지는 존재하지 않습니다.");
        }

        FurnitureImage furnitureImage = furnitureImageRepository.findById(furnitureImageId);
        if (furnitureImage.isDeleted()) {
            throw new AlreadyDeleteException("해당 가구 이미지는 이미 삭제되었습니다.");
        }

        if (furnitureImage.getRoom().getRoomCreator().getMemberCode().equals(memberCode) == false) {
            throw new NotSameWriterException("방 작성자와 요청자가 다릅니다.");
        }

        furnitureImage.softDelete();
        furnitureImageRepository.save(furnitureImage);

        return furnitureImage.getId();

    }

}
