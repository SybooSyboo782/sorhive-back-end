package com.sorhive.comprojectserver.room.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.application.service.AuthService;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookCreateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookResponseDto;
import com.sorhive.comprojectserver.room.command.application.dto.GuestBookUpdateRequestDto;
import com.sorhive.comprojectserver.room.command.domain.furnitureimage.FurnitureImage;
import com.sorhive.comprojectserver.room.command.domain.guestbook.GuestBook;
import com.sorhive.comprojectserver.room.command.domain.guestbook.GuestBookWriter;
import com.sorhive.comprojectserver.room.command.domain.guestbook.GuestBookWriterService;
import com.sorhive.comprojectserver.room.command.domain.repository.FurnitureImageRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.GuestBookRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.RoomRepository;
import com.sorhive.comprojectserver.room.command.domain.room.Room;
import com.sorhive.comprojectserver.room.execption.NoFurnitureImageException;
import com.sorhive.comprojectserver.room.execption.NoGuestBookException;
import com.sorhive.comprojectserver.room.execption.NoRoomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
public class RoomService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final RoomRepository roomRepository;
    private final GuestBookRepository guestBookRepository;
    private final GuestBookWriterService guestBookWriterService;
    private final FurnitureImageRepository furnitureImageRepository;
    private final TokenProvider tokenProvider;


    public RoomService(RoomRepository roomRepository, GuestBookRepository guestBookRepository, GuestBookWriterService guestBookWriterService, FurnitureImageRepository furnitureImageRepository, TokenProvider tokenProvider) {
        this.roomRepository = roomRepository;
        this.guestBookRepository = guestBookRepository;
        this.guestBookWriterService = guestBookWriterService;
        this.furnitureImageRepository = furnitureImageRepository;
        this.tokenProvider = tokenProvider;
    }

    /** 방명록 생성 */
    public GuestBookResponseDto createGuestBook(String accessToken, GuestBookCreateRequestDto guestBookCreateRequestDto) {

        log.info("[RoomService] createRoom Start ===================================");
        log.info("[RoomService] roomCreateDto {}", guestBookCreateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 방명록 작성자 생성 */
        GuestBookWriter guestBookWriter = guestBookWriterService.createGuestBookWriter(new MemberCode(memberCode));

        /* 요청 객체에서 값 꺼내오기 */
        String guestBookContent = guestBookCreateRequestDto.getGuestBookContent();
        Long roomId = guestBookCreateRequestDto.getRoomId();

        /* 방번호에 맞는 방 찾기 */
        Room room = roomRepository.findById(roomId);

        if(room == null) {
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
        GuestBookResponseDto guestBookResponseDto = new GuestBookResponseDto();

        /* 응답 객체에 정보 넣기 */
        guestBookResponseDto.setGuestBookId(guestBook.getId());
        guestBookResponseDto.setGuestBookContent(guestBook.getContent());
        guestBookResponseDto.setCreateTime(guestBook.getCreateTime());
        guestBookResponseDto.setGuestBookWriterCode(guestBook.getGuestBookWriter().getMemberCode().getValue());
        guestBookResponseDto.setGuestBookWriterName(guestBook.getGuestBookWriter().getName());
        guestBookResponseDto.setGuestBookWriterId(guestBook.getGuestBookWriter().getId());
        guestBookResponseDto.setRoomId(guestBook.getRoom().getId());

        log.info("[RoomService] guestBookCreateResponseDto {}", guestBookResponseDto);
        log.info("[RoomService] createRoom End ===================================");

        return guestBookResponseDto;
    }

    /** 방명록 수정 */
    public GuestBookResponseDto updateGuestBook(String accessToken, GuestBookUpdateRequestDto guestBookUpdateRequestDto) {

        log.info("[RoomService] updateGuestBook Start ===================================");
        log.info("[RoomService] guestBookUpdateRequestDto {}", guestBookUpdateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 요청 객체에서 값 꺼내오기 */
        String guestBookContent = guestBookUpdateRequestDto.getContent();
        Long guestBookId = guestBookUpdateRequestDto.getGuestBookId();

        if(guestBookRepository.findById(guestBookId) == null) {
            throw new NoGuestBookException("해당 방명록은 존재하지 않습니다.");
        }

        GuestBook guestBookFind = guestBookRepository.findById(guestBookId);
        if(guestBookFind.getDeleteYn() == 'Y') {
            throw new AlreadyDeleteException("해당 방명록은 이미 삭제 됐습니다.");
        }

        /* 방번호에 맞는 방 찾기 */
        Optional<GuestBook> guestBookData = guestBookRepository.findByIdAndDeleteYnEquals(guestBookId, 'N');
        GuestBook guestBook = guestBookData.get();

        if(guestBook.getGuestBookWriter().getMemberCode().getValue() != memberCode) {
            throw new NotSameWriterException("방명록 작성자와 요청자가 다릅니다");
        }

        guestBook.updateGuestBook(guestBookContent);

        /* 방명록 저장하기 */
        guestBookRepository.save(guestBook);

        /* 방명록 응답 전송 객체 생성 */
        GuestBookResponseDto guestBookResponseDto = new GuestBookResponseDto();

        /* 응답 객체에 정보 넣기 */
        guestBookResponseDto.setGuestBookId(guestBook.getId());
        guestBookResponseDto.setGuestBookContent(guestBook.getContent());
        guestBookResponseDto.setCreateTime(guestBook.getCreateTime());
        guestBookResponseDto.setGuestBookWriterCode(guestBook.getGuestBookWriter().getMemberCode().getValue());
        guestBookResponseDto.setGuestBookWriterName(guestBook.getGuestBookWriter().getName());
        guestBookResponseDto.setGuestBookWriterId(guestBook.getGuestBookWriter().getId());
        guestBookResponseDto.setRoomId(guestBook.getRoom().getId());

        log.info("[RoomService] guestBookUpdateRequestDto {}", guestBookResponseDto);
        log.info("[RoomService] updateGuestBook End ===================================");

        return guestBookResponseDto;
    }

    /** 방명록 삭제 */
    public Object deleteGuestBook(String accessToken, Long guestBookId) {

        log.info("[RoomService] deleteGuestBook Start ===================================");
        log.info("[RoomService] guestBookId ", guestBookId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(guestBookRepository.findById(guestBookId) == null) {
            throw new NoGuestBookException("해당 방명록은 존재하지 않습니다.");
        }

        GuestBook guestBookFind = guestBookRepository.findById(guestBookId);
        if(guestBookFind.getDeleteYn() == 'Y') {
            throw new AlreadyDeleteException("해당 방명록은 이미 삭제 됐습니다.");
        }

        Optional<GuestBook> guestBookData = guestBookRepository.findByIdAndDeleteYnEquals(guestBookId, 'N');
        GuestBook guestBook = guestBookData.get();

        Long guestBookWriterCode = guestBook.getGuestBookWriter().getMemberCode().getValue();

        if(!guestBookWriterCode.equals(memberCode)) {
            throw new NotSameWriterException("방명록 작성자와 요청자가 다릅니다");
        }

        guestBook.deleteGuestBook('Y');
        guestBookRepository.save(guestBook);

        return guestBook.getId();

    }

    /** 가구 이미지 제거 */
    public Object deleteFurnitureImage(String accessToken, Long furnitureImageId) {

        log.info("[RoomService] deleteFurnitureImage Start ===================================");
        log.info("[RoomService] furnitureImageId ", furnitureImageId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(furnitureImageRepository.findById(furnitureImageId) == null) {
            throw new NoFurnitureImageException("해당 가구 이미지는 존재하지 않습니다.");
        }

        FurnitureImage furnitureImage = furnitureImageRepository.findById(furnitureImageId);
        if(furnitureImage.getDeleteYn() == 'Y') {
            throw new AlreadyDeleteException("해당 가구 이미지는 이미 삭제되었습니다.");
        }

        if(furnitureImage.getRoom().getRoomCreator().getMemberCode().getValue() != memberCode) {
            throw new NotSameWriterException("방 작성자와 요청자가 다릅니다.");
        }

        furnitureImage.delete();
        furnitureImageRepository.save(furnitureImage);

        return furnitureImage.getId();

    }

}
