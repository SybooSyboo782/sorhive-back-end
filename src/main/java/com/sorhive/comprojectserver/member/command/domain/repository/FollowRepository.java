package com.sorhive.comprojectserver.member.command.domain.repository;

import com.sorhive.comprojectserver.member.command.domain.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * <pre>
 * Class : FollowRepository
 * Comment: 팔로우 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-10       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowIdAndDeletedEquals(Long id, boolean n);


    @Query(value = "select follow " +
            "from Follow follow " +
            "where follow.deleted = false " +
            "and follow.followingId = :followingId " +
            "and follow.followerId = :followerId")
    Optional<Follow> findByFollowingId(@Param("followerId")Long followerId, @Param("followingId")Long followingId);

}
