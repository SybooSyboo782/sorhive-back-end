package com.sorhive.comprojectserver.member.query.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <pre>
 * Class : FollowDataDao
 * Comment: 팔로우 데이터 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-10       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public interface FollowDataDao extends JpaRepository<FollowData, Long> {

    @Query(value = "select follow " +
                     "from FollowData follow " +
                    "where follow.deleted = false " +
                      "and follow.followerId = :followerId")
    List<FollowData> findByFollowerId(@Param("followerId")Long followerId);

    @Query(value = "select follow " +
            "from FollowData follow " +
            "where follow.deleted = false " +
            "and follow.followingId = :followingId")
    List<FollowData> findByFollowingId(@Param("followingId")Long followingId);

}
