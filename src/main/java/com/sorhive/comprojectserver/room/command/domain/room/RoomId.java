package com.sorhive.comprojectserver.room.command.domain.room;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : RoomId
 * Comment: 방 아이디 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Access(AccessType.FIELD)
public class RoomId implements Serializable {

    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long value;

    protected RoomId() {
    }

    public RoomId(Long value) { this.value = value; }

    public Long getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId roomId = (RoomId) o;
        return Objects.equals(value, roomId.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static RoomId of(Long value) { return new RoomId(value); }
}