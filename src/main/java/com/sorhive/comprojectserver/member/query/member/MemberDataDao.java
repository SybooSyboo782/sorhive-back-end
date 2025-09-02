package com.sorhive.comprojectserver.member.query.member;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * Class : MemberDataDAO
 * Comment: 멤버 데이터 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-31       부시연           최초 생성
 * 2022-11-12       부시연           회원 아이디 검색 시 자기 자신 제외 기능 추가
 * 2022-11-12       부시연           자신을 제외한 전체 회원 목록 조회
 * 2022-11-13       부시연           자신을 제외한 멤버 코드 조회
 * 2022-11-26       부시연           회원 역할에 대해 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public interface MemberDataDao extends JpaRepository<MemberData, String> {

    MemberData findByIdAndDeletedAndMemberRole(String memberId, boolean deletedYn, MemberRole role);

    List<MemberData> findAllByMemberCodeIsNotAndDeletedAndMemberRole(Long memberCode, boolean deletedYn, MemberRole role);

    List<MemberData> findByIdLikeAndMemberCodeIsNotAndMemberRole(String memberId, Long memberCode, MemberRole role);

    Optional<MemberData> findByMemberCodeAndMemberRole(Long memberCode, MemberRole role);

    MemberData findFirstByMemberCodeAndDeletedAndMemberRole(Long memberCode, boolean deletedYn, MemberRole role);

    MemberData findByMemberCodeAndMemberCodeIsNotAndMemberRole(Long memberCode, Long temp, MemberRole role);
}
