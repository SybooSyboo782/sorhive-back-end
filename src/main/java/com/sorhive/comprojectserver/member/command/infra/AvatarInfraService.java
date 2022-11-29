package com.sorhive.comprojectserver.member.command.infra;

import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.file.S3MemberFile;
import com.sorhive.comprojectserver.member.command.application.dto.AvatarRequestDto;
import com.sorhive.comprojectserver.member.command.domain.model.avatar.Avatar;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import com.sorhive.comprojectserver.member.command.domain.repository.AvatarRepository;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.room.command.domain.repository.RoomRepository;
import com.sorhive.comprojectserver.room.command.domain.room.Room;
import com.sorhive.comprojectserver.room.command.domain.room.RoomCreator;
import com.sorhive.comprojectserver.room.command.domain.room.RoomCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

/**
 * <pre>
 * Class : AvatarInfraService
 * Comment: 아바타 인프라 서비스(외부 서버와 연동 : 파일서버)
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-11       부시연           바이트배열로 대응
 * 2022-11-22       부시연           기본방 설정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class AvatarInfraService {

    private static final Logger log = LoggerFactory.getLogger(AvatarInfraService.class);
    private final AvatarRepository avatarRepository;
    private final TokenProvider tokenProvider;
    private final RoomCreatorService roomCreatorService;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private S3MemberFile s3MemberFile;

    public AvatarInfraService(AvatarRepository avatarRepository, TokenProvider tokenProvider, RoomCreatorService roomCreatorService, RoomRepository roomRepository, MemberRepository memberRepository, S3MemberFile s3MemberFile) {
        this.avatarRepository = avatarRepository;
        this.tokenProvider = tokenProvider;
        this.roomCreatorService = roomCreatorService;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.s3MemberFile = s3MemberFile;
    }

    /** 아바타 생성하기 */
    @Transactional
    public Long createAvatar(String accessToken, AvatarRequestDto avatarRequestDto) {

        log.info("[AvatarService] createAvatar Start ===============");
        log.info("[AvatarService] avatarRequestDto : " + avatarRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        String memberAvatarImageName = "avatar_" + memberCode + ".png";

        if(avatarRequestDto.getMemberAvatarImage() == null) {
            throw new NoContentException("아바타 이미지가 없습니다.");
        }

        byte[] avatarImage = avatarRequestDto.getMemberAvatarImage();

        try {
            if(avatarImage != null) {
                Optional<Member> memberData = memberRepository.findByMemberCode(memberCode);
                Member member = memberData.get();
                member.createAvatarImagePath(s3MemberFile.upload(avatarImage, "images", memberAvatarImageName));
                memberRepository.save(member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Avatar avatar = new Avatar(
                memberCode,
                avatarRequestDto.getFaceType(),
                avatarRequestDto.getEyeType(),
                avatarRequestDto.getEyeBrowsType(),
                avatarRequestDto.getHairType()
        );

        avatarRepository.save(avatar);

        /** 기본방 설정 */
        RoomCreator roomCreator = roomCreatorService.createRoomCreator(new MemberCode(memberCode));

        Room defaultRoom = new Room(
                memberCode,
                "637c67b716de470e1cecef26",
                roomCreator
        );

        /* 방에 몽고 DB의 기본 방 값을 포함하여 저장하기 */
        roomRepository.save(defaultRoom);

        return memberCode;
    }

    /** 아바타 수정하기 */
    @Transactional
    public Long updateAvatar(String accessToken, AvatarRequestDto avatarRequestDto) {

        log.info("[AvatarService] updateAvatar Start ===============");
        log.info("[AvatarService] avatarRequestDto : " + avatarRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        String memberAvatarImageName = "avatar_" + memberCode + ".png";

        if(avatarRequestDto.getMemberAvatarImage() == null) {
            throw new NoContentException("아바타 이미지가 없습니다.");
        }

        byte[] avatarImage = avatarRequestDto.getMemberAvatarImage();

        try {
            if(avatarImage != null) {
                Optional<Member> memberData = memberRepository.findByMemberCode(memberCode);
                Member member = memberData.get();
                member.setAvatarImagePath(s3MemberFile.upload(avatarImage, "images", memberAvatarImageName));
                memberRepository.save(member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Avatar avatar = new Avatar(
                memberCode,
                avatarRequestDto.getFaceType(),
                avatarRequestDto.getEyeType(),
                avatarRequestDto.getEyeBrowsType(),
                avatarRequestDto.getHairType()
        );

        avatarRepository.save(avatar);

        return memberCode;
    }
}
