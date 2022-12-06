package boilerplate.sample.post.domain.command

data class CreatePostCommand(
    val userId: Long,
    val title: String,
    val body: String,
)
