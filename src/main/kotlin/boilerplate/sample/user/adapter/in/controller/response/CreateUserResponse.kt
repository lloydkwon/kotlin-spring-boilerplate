package boilerplate.sample.user.adapter.`in`.controller.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserResponse(
    val id: Long?,
    val name: String,
)
