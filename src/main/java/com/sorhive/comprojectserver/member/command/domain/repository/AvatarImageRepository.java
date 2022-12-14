package com.sorhive.comprojectserver.member.command.domain.repository;

import com.sorhive.comprojectserver.member.command.domain.model.avatarimage.AvatarImage;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * <pre>
 * Class : AvatarImageRepository
 * Comment: 아바타 이미지 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public interface AvatarImageRepository extends Repository<AvatarImage, Long> {

    Optional<AvatarImage> findById(Long id);
    void save(AvatarImage avatarImage);

}
