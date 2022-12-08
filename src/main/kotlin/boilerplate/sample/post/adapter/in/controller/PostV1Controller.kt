package boilerplate.sample.post.adapter.`in`.controller

import boilerplate.sample.core.resolver.CurrentUser
import boilerplate.sample.core.resolver.TokenAuth
import boilerplate.sample.post.adapter.`in`.controller.request.CreatePostRequest
import boilerplate.sample.post.adapter.`in`.controller.response.CreatePostResponse
import boilerplate.sample.post.domain.command.CreatePostCommand
import boilerplate.sample.post.domain.port.`in`.CreatePostUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostV1Controller(
    private val createPostUseCase: CreatePostUseCase,
) {
    @PostMapping("")
    fun createPost(@RequestBody @Valid request: CreatePostRequest, @TokenAuth user: CurrentUser) {
        val command = CreatePostCommand(title = request.title, body = request.body, userId = 1L)
        val post = createPostUseCase.execute(command)
        val response = CreatePostResponse(id = post.id, title = post.title, body = post.body)
    }
}
