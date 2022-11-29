package com.sorhive.comprojectserver.room.command.infra;

import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.file.S3FurnitureImageFile;
import com.sorhive.comprojectserver.file.S3MemberFile;
import com.sorhive.comprojectserver.member.command.application.service.AuthService;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.room.command.application.dto.FurnitureImageCreateRequestDto;
import com.sorhive.comprojectserver.room.command.application.dto.FurnitureImageCreateResponseDto;
import com.sorhive.comprojectserver.room.command.application.dto.RoomCreateDto;
import com.sorhive.comprojectserver.room.command.domain.furnitureimage.FurnitureImage;
import com.sorhive.comprojectserver.room.command.domain.repository.FurnitureImageRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.MongoRoomRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.RoomRepository;
import com.sorhive.comprojectserver.room.command.domain.room.MongoRoom;
import com.sorhive.comprojectserver.room.command.domain.room.Room;
import com.sorhive.comprojectserver.room.command.domain.room.RoomCreator;
import com.sorhive.comprojectserver.room.command.domain.room.RoomCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * <pre>
 * Class : RoomInfraService
 * Comment: 방 인프라 서비스 (몽고 DB 저장, S3 파일 서버 통신)
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-09       부시연           방 생성 추가
 * 2022-11-10       부시연           방이미지 생성 추가
 * 2022-11-17       부시연
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class RoomInfraService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final MongoRoomRepository mongoRoomRepository;
    private final RoomRepository roomRepository;
    private final RoomCreatorService roomCreatorService;
    private final MemberRepository memberRepository;
    private final FurnitureImageRepository furnitureImageRepository;
    private final TokenProvider tokenProvider;
    private final S3FurnitureImageFile s3FurnitureImageFile;
    private final S3MemberFile s3MemberFile;


    public RoomInfraService(MongoRoomRepository mongoRoomRepository, RoomRepository roomRepository, RoomCreatorService roomCreatorService, MemberRepository memberRepository, FurnitureImageRepository furnitureImageRepository, TokenProvider tokenProvider, S3FurnitureImageFile s3FurnitureImageFile, S3MemberFile s3MemberFile) {
        this.mongoRoomRepository = mongoRoomRepository;
        this.roomRepository = roomRepository;
        this.roomCreatorService = roomCreatorService;
        this.memberRepository = memberRepository;
        this.furnitureImageRepository = furnitureImageRepository;
        this.tokenProvider = tokenProvider;
        this.s3FurnitureImageFile = s3FurnitureImageFile;
        this.s3MemberFile = s3MemberFile;
    }

    /** 방 생성 */
    @Transactional
    public String createRoom(String accessToken, RoomCreateDto roomCreateDto) {
        log.info("[RoomInfraService] createRoom Start ===================================");
        log.info("[RoomInfraService] roomCreateDto {}", roomCreateDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(roomCreateDto.getRoomImage() == null) {
            throw new NoContentException("방 이미지가 없습니다.");
        }

        /* 방이미지 바이트배열 꺼내기 */
        byte[] roomImage = roomCreateDto.getRoomImage();

        try {
            if(roomImage != null) {

                /* 방이미지 S3에 저장하고 회원에 방 이미지 저장하기 */
                Optional<Member> memberData = memberRepository.findByMemberCode(memberCode);
                Member member = memberData.get();
                member.setRoomImagePath(s3MemberFile.upload(roomImage, "images", "room_" + memberCode + ".png"));
                memberRepository.save(member);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* 방작성자 생성 */
        RoomCreator roomCreator = roomCreatorService.createRoomCreator(new MemberCode(memberCode));

        /* 몽고 DB 방 생성 */
        MongoRoom mongoRoom = new MongoRoom(
                roomCreator,
                roomCreateDto.getFurnitures()
        );

        /* 몽고 DB 방 저장 */
        mongoRoomRepository.save(mongoRoom);

        /* 기존 몽고 DB 방이 있으면 찾아서 삭제하기 */
        Room oldRoom = roomRepository.findById(memberCode);
        if(oldRoom != null && oldRoom.getRoomNo() != "637befb055f63397c8692bb2") {
            mongoRoomRepository.deleteById(oldRoom.getRoomNo());
        }

        /* 방 생성하기 */
        Room newRoom = new Room(
                roomCreator.getMemberCode().getValue(),
                mongoRoom.getId(),
                roomCreator
        );

        /* 방에 몽고 DB에서 저장한 값을 포함하여 저장하기 */
        roomRepository.save(newRoom);

        log.info("[RoomInfraService] createRoom End ==============================");

        return mongoRoom.getId();

    }

    /** 가구 이미지 생성 */
    public FurnitureImageCreateResponseDto createFurnitureImage(String accessToken, FurnitureImageCreateRequestDto furnitureImageCreateRequestDto) {

        log.info("[RoomInfraService] createFurnitureImage Start ===================================");
        log.info("[RoomInfraService] furnitureImageCreateDto {}", furnitureImageCreateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(furnitureImageCreateRequestDto.getRoomId() != memberCode) {
            throw new NotSameWriterException("해당 사람은 방 주인이 아닙니다.");
        }

        if((furnitureImageCreateRequestDto.getFurnitureImage() == null)||(furnitureImageCreateRequestDto.getFurnitureImageNo() == null)||(furnitureImageCreateRequestDto.getFurnitureImageName() == null) ) {
            throw new NoContentException("이미지 정보값이 없습니다.");
        }

        /* 가구 이미지 생성 응답 전송 객체 생성 */
        FurnitureImageCreateResponseDto furnitureImageCreateResponseDto = null;

        try {

            Room room = roomRepository.findById(furnitureImageCreateRequestDto.getRoomId());

            byte[] furnitureByteImage = furnitureImageCreateRequestDto.getFurnitureImage();
            String originalName = furnitureImageCreateRequestDto.getFurnitureImageName();
            String changeName = UUID.randomUUID() + " - furniture_" + memberCode + ".png";
            Integer imageNo = furnitureImageCreateRequestDto.getFurnitureImageNo();

            /* 가구 이미지를 S3에 저장하고 URL 값을 반환 받는다. */
            String furnitureImagePath = s3FurnitureImageFile.upload(furnitureByteImage, changeName, "furnitures");

            /* 가구 이미지 생성 */
            FurnitureImage furnitureImage = new FurnitureImage(
                    furnitureImagePath,
                    originalName,
                    changeName,
                    imageNo,
                    room
            );

            furnitureImageRepository.save(furnitureImage);

            furnitureImageCreateResponseDto = new FurnitureImageCreateResponseDto(
                    room.getId(),
                    furnitureImage.getId(),
                    furnitureImage.getPath(),
                    furnitureImage.getImageNo()
            );

            return furnitureImageCreateResponseDto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
