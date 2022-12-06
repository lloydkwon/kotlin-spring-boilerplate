package boilerplate.sample.post.adapter.out.persistence.repository

import boilerplate.sample.post.adapter.out.persistence.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>, PostQuerydslRepository {
}
