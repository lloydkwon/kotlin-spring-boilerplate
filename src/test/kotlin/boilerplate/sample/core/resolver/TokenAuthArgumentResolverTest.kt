package boilerplate.sample.core.resolver

import boilerplate.sample.core.util.JwtTokenUtil
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebMvcTest
class TokenAuthArgumentResolverTest : BehaviorSpec({
    @RestController
    class TestController {
        @GetMapping("/test")
        fun test(@TokenAuth user: CurrentUser): Long? {
            return user.userId
        }
    }

    given("TokenAuthArgumentResolver") {
        val testController = TestController()
        val jwtTokenUtil = JwtTokenUtil(
            secretKey = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            algorithm = "HS256",
        )
        val tokenAuthArgumentResolver = TokenAuthArgumentResolver(jwtTokenUtil)
        val mockMvc = MockMvcBuilders.standaloneSetup(testController)
            .setCustomArgumentResolvers(tokenAuthArgumentResolver)
            .build()
        `when`("헤더에 올바른 토큰이 존재하는 경우") {
            val token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxfQ.t-dL6resmtNGqurhA94e3BuBmm00PB8aooh9EmXCI-A"
            then("CurrentUser에 user_id가 존재한다") {
                mockMvc.get("/test") {
                    header(name = "Authorization", "Bearer $token")
                }
                    .andExpect {
                        status { isOk() }
                        content { string("1") }
                    }
            }
        }
        `when`("헤더에 올바르지 않은 토큰이 존재하는 경우") {
            val token = "test"
            then("CurrentUser에 user_id가 null이다") {
                mockMvc.get("/test") {
                    header(name = "Authorization", "Bearer $token")
                }
                    .andExpect {
                        status { isOk() }
                        content { string("") }
                    }
            }
        }
    }
})