package boilerplate.sample.user.adapter.out.persistence.repository

import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>, UserQuerydslRepository {
    fun findByName(name: String): UserEntity?
}