package com.sorhive.comprojectserver.room.command.domain.repository;

import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : RoomRepository
 * Comment: 방 레포지토리
 * History
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-03       부시연           RoomRepository 최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public interface RoomRepository extends Repository<Room, Long> {
    void save(Room room);
    Room findById(Long roomId);
}