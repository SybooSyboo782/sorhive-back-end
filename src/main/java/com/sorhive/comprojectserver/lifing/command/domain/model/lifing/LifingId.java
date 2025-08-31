package com.sorhive.comprojectserver.lifing.command.domain.model.lifing;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : LifingId
 * Comment: 라이핑 아이디 도메인 모델
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
public class LifingId implements Serializable {

    @Column(name="lifing_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long value;

    protected LifingId() {
    }

    public LifingId(Long value){ this.value = value; }

    public Long getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifingId storyId = (LifingId) o;
        return Objects.equals(value, storyId.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static LifingId of(Long value) { return new LifingId(value); }

}