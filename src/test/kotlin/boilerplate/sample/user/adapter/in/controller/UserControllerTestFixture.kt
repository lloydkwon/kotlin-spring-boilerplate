package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.domain.model.User

fun makeUser(id: Long?, name: String, password: String): User {
    return User(id = id, name = name, password = password)
}
