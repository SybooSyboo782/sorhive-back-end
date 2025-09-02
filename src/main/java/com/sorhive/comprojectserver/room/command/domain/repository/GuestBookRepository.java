package com.sorhive.comprojectserver.room.command.domain.repository;

import com.sorhive.comprojectserver.room.command.domain.model.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <pre>
 * Class : GuestBookRepository
 * Comment: 방명록 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * 2022-11-13       부시연           save 추가
 * 2022-11-16       부시연           findById 추가
 * 2022-11-16       부시연           findByIdAndDeletedEquals 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    Optional<GuestBook> findByIdAndDeletedEquals(Long guestBookId, boolean deletedYn);
}
