package boilerplate.sample.user.adapter.out.persistence.entity

import boilerplate.sample.core.entity.BaseTimestampEntity
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class UserEntity(
    val name: String,
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null
) : BaseTimestampEntity()
