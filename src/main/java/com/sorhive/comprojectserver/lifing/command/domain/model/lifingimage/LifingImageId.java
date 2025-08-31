package com.sorhive.comprojectserver.lifing.command.domain.model.lifingimage;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : LifingImageId
 * Comment: 라이핑 이미지 아이디 도메인 모델
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
public class LifingImageId implements Serializable {

    @Column(name="lifing_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long value;

    protected LifingImageId() {
    }

    public LifingImageId(Long value) { this.value = value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifingImageId that = (LifingImageId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static LifingImageId of(Long value) { return new LifingImageId(value); }
}