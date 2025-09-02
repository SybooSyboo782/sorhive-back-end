package com.sorhive.comprojectserver.feed.command.domain.repository;

import com.sorhive.comprojectserver.feed.command.domain.model.FeedHoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <pre>
 * Class : FeedHoneyRepository
 * Comment: 피드 허니 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-16       부시연           허니 구분을 위해 피드 허니로 명칭 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface FeedHoneyRepository extends JpaRepository<FeedHoney, Long> {
    
    Optional<FeedHoney> findByMemberMemberCodeAndFeedFeedIdAndDeletedEquals(Long memberCode, Long feedId, boolean deletedYn);

}
