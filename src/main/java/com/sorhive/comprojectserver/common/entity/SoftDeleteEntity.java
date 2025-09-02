package com.sorhive.comprojectserver.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class SoftDeleteEntity extends BaseEntity {

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // soft delete 메소드
    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // restore (삭제 취소)
    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
    }

}