package boilerplate.sample.post.application.service

import boilerplate.sample.post.adapter.out.internal.UserApplication
import boilerplate.sample.post.adapter.out.persistence.entity.PostEntity
import boilerplate.sample.post.adapter.out.persistence.repository.PostRepository
import boilerplate.sample.post.domain.command.CreatePostCommand
import boilerplate.sample.post.domain.converter.PostConverter
import boilerplate.sample.post.domain.model.Post
import boilerplate.sample.post.domain.port.`in`.CreatePostUseCase
import boilerplate.sample.user.domain.command.GetUserCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreatePostService(
    private val userApplication: UserApplication,
    private val postRepository: PostRepository,
) : CreatePostUseCase {
    override fun execute(command: CreatePostCommand): Post {
        val getUserCommand = GetUserCommand(userId = command.userId)
        val user = userApplication.getUser(getUserCommand)
        val post = PostEntity(title = command.title, body = command.body, userId = user.id)
        postRepository.save(post)
        return PostConverter.from(post)
    }
}
