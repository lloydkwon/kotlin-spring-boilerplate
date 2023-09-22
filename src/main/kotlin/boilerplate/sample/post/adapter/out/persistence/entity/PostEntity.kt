package boilerplate.sample.post.adapter.out.persistence.entity

import boilerplate.sample.core.entity.BaseTimestampEntity
import jakarta.persistence.*

@Entity
@Table(name = "`post`")
class PostEntity(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "body", nullable = false)
    val body: String,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Id
    @GeneratedValue
    val id: Long = 0,
) : BaseTimestampEntity()
