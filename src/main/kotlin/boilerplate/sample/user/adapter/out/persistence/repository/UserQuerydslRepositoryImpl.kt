package boilerplate.sample.user.adapter.out.persistence.repository

import com.querydsl.jpa.impl.JPAQueryFactory

class UserQuerydslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : UserQuerydslRepository {
}
