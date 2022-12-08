package boilerplate.sample.post.domain.port.`in`

import boilerplate.sample.post.domain.command.CreatePostCommand
import boilerplate.sample.post.domain.model.Post

interface CreatePostUseCase {
    fun execute(command: CreatePostCommand): Post
}
