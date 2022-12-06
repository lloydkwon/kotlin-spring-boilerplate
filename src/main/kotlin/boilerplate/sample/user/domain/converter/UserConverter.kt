package boilerplate.sample.user.domain.converter

import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import boilerplate.sample.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class UserConverter {
    companion object {
        fun from(user: UserEntity): User {
            return User(
                name = user.name,
                password = user.password,
                id = user.id,
            )
        }

        fun of(user: User): UserEntity {
            return UserEntity(
                name = user.name,
                password = user.password,
                id = user.id,
            )
        }
    }
}
