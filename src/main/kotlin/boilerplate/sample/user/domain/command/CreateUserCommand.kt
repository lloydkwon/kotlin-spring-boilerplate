package boilerplate.sample.user.domain.command

data class CreateUserCommand(
    val password: String,
    val name: String,
)