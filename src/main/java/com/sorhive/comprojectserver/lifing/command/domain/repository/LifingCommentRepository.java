package com.sorhive.comprojectserver.lifing.command.domain.repository;

import com.sorhive.comprojectserver.lifing.command.domain.model.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.LifingComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * Class : LifingCommentRepository
 * Comment: 라이핑 댓글 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-16       부시연           라이핑 댓글 삭제
 * 2022-11-17       부시연           라이핑을 통한 댓글 찾기 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface LifingCommentRepository extends JpaRepository<LifingComment, Long> {

    LifingComment findByLifingCommentId(Long lifingCommentId);
    Optional<LifingComment> findByLifingCommentIdAndDeletedEquals(Long lifingCommentId, boolean deletedYn);
    List<LifingComment> findByLifing(Lifing lifing);
}
