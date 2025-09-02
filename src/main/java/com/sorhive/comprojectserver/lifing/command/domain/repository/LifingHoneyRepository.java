package com.sorhive.comprojectserver.lifing.command.domain.repository;

import com.sorhive.comprojectserver.lifing.command.domain.model.LifingHoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <pre>
 * Class : LifingHoneyRepository
 * Comment: 허니 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-16       부시연           허니 구분을 위해 라이핑 허니로 명칭 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface LifingHoneyRepository extends JpaRepository<LifingHoney, Long> {
    Optional<LifingHoney> findByMemberMemberCodeAndLifingLifingIdAndDeletedEquals(Long memberCode, Long lifingId, boolean deletedYn);

}
