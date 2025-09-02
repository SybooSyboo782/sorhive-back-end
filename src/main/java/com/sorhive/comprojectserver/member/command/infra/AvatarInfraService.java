package com.sorhive.comprojectserver.member.command.infra;

import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.file.S3MemberFile;
import com.sorhive.comprojectserver.member.command.application.dto.AvatarRequestDto;
import com.sorhive.comprojectserver.member.command.domain.model.Avatar;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.AvatarRepository;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import com.sorhive.comprojectserver.room.command.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarInfraService {

    @Value( "${custom.default-room-code}")
    private String defaultRoomNo;
    private final AvatarRepository avatarRepository;
    private final TokenProvider tokenProvider;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private S3MemberFile s3MemberFile;

    /** 아바타 생성하기 */
    @Transactional
    public Long createAvatar(String accessToken, AvatarRequestDto avatarRequestDto) {

        log.info("[AvatarService] createAvatar Start ===============");
        log.info("[AvatarService] avatarRequestDto : " + avatarRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        String memberAvatarImageName = "avatar_" + memberCode + ".png";

        if (avatarRequestDto.getMemberAvatarImage() == null) {
            throw new NoContentException("아바타 이미지가 없습니다.");
        }

        byte[] avatarImage = avatarRequestDto.getMemberAvatarImage();

        Member member = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        try {
            if (avatarImage != null) {
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
        Room defaultRoom = new Room(
                memberCode,
                defaultRoomNo,
                member
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

        if (avatarRequestDto.getMemberAvatarImage() == null) {
            throw new NoContentException("아바타 이미지가 없습니다.");
        }

        byte[] avatarImage = avatarRequestDto.getMemberAvatarImage();

        try {
            if (avatarImage != null) {
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
