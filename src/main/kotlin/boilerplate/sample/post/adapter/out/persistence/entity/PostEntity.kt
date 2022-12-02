package boilerplate.sample.post.adapter.out.persistence.entity

import boilerplate.sample.core.entity.BaseTimestampEntity
import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import jakarta.persistence.*

@Entity
class PostEntity(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "body", nullable = false)
    val body: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val user: UserEntity,

    @Id
    @GeneratedValue
    val id: Long? = null,
) : BaseTimestampEntity()
