package boilerplate.sample.core.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimestampEntity(
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
) {
    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        updatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now()
    }
}
