package com.sorhive.comprojectserver.member.command.domain.repository;

import com.sorhive.comprojectserver.member.command.domain.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * Class : AvatarRepository
 * Comment: 아바타 레포지토리
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
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
