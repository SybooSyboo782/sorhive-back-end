package com.sorhive.comprojectserver.member.query.avatar;

import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : AvatarDataDao
 * Comment: 아바타 조회용 DAO
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface AvatarDataDao extends Repository<AvatarData, Long> {

    AvatarData findByMemberCode(Long memberCode);
}
