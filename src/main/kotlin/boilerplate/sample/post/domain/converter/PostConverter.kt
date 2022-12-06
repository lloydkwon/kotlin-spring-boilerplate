package boilerplate.sample.post.domain.converter

import boilerplate.sample.post.adapter.out.persistence.entity.PostEntity
import boilerplate.sample.post.domain.model.Post
import org.springframework.stereotype.Component

@Component
class PostConverter {
    companion object {
        fun from(post: PostEntity): Post {
            return Post(
                title = post.title,
                body = post.body,
                userId = post.userId,
                id = post.id,
            )
        }

        fun of(post: Post): PostEntity {
            return PostEntity(
                title = post.title,
                body = post.body,
                userId = post.userId,
                id = post.id,
            )
        }
    }
}
