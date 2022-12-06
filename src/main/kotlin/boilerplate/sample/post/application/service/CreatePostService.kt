package boilerplate.sample.post.application.service

import boilerplate.sample.post.adapter.out.persistence.repository.PostRepository
import boilerplate.sample.post.domain.command.CreatePostCommand
import boilerplate.sample.post.domain.port.`in`.CreatePostUseCase
import org.springframework.stereotype.Service

@Service
class CreatePostService(
    private val postRepository: PostRepository,
) : CreatePostUseCase {
    override fun execute(command: CreatePostCommand) {
        TODO("Not yet implemented")
    }
}
