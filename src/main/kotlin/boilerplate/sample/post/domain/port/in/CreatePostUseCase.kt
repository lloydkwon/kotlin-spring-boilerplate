package boilerplate.sample.post.domain.port.`in`

import boilerplate.sample.post.domain.command.CreatePostCommand

interface CreatePostUseCase {
    fun execute(command: CreatePostCommand)
}
