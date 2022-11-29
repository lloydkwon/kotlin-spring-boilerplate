package boilerplate.sample.user.domain.model

data class User(
    val name: String,
    val password: String,
    val id: Long? = null,
)
