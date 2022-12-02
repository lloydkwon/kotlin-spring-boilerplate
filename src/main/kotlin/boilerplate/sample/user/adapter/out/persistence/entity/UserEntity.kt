package boilerplate.sample.user.adapter.out.persistence.entity

import boilerplate.sample.core.entity.BaseTimestampEntity
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class UserEntity(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null
) : BaseTimestampEntity()
