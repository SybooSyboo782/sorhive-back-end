package com.sorhive.comprojectserver.feed.command.domain.repository;

import com.sorhive.comprojectserver.feed.command.domain.model.feed.Feed;
import com.sorhive.comprojectserver.feed.command.domain.model.feedcomment.FeedComment;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * Class : FeedCommentRepository
 * Comment: 피드 댓글 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-19       부시연           피드 댓글 아이디와 삭제여부로 조회
 * 2022-11-19       부시연           피드 아이디로 피드 댓글 조회
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface FeedCommentRepository extends Repository<FeedComment, Long> {

    void save(FeedComment feedComment);

    FeedComment findById(Long feedCommentId);

    Optional<FeedComment> findByIdAndDeleteYnEquals(Long feedCommentId, char n);

    List<FeedComment> findByFeed(Feed feed);
}
