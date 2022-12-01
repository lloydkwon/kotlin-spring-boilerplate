package boilerplate.sample.core.interceptor

import boilerplate.sample.core.util.JwtTokenUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import jakarta.servlet.ServletException
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebMvcTest
class TokenAuthInterceptorTest : BehaviorSpec({
    @RestController
    class TestController {
        @GetMapping("/test")
        fun test(): ResponseEntity<Any> {
            return ResponseEntity.ok().build()
        }
    }

    given("TokenAuthInterceptor") {
        val testController = TestController()
        val jwtTokenUtil = JwtTokenUtil(
            secretKey = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            algorithm = "HS256",
        )
        val tokenAuthInterceptor = TokenAuthInterceptor(
            authHeader = "Authorization",
            jwtTokenUtil = jwtTokenUtil,
        )
        val mockMvc = MockMvcBuilders.standaloneSetup(testController)
            .addInterceptors(tokenAuthInterceptor)
            .build()

        `when`("올바른 토큰을 사용하는 경우") {
            val token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxfQ.t-dL6resmtNGqurhA94e3BuBmm00PB8aooh9EmXCI-A"
            then("요청이 정상 동작한다") {
                mockMvc.perform(
                    get("/test")
                        .header(
                            "Authorization",
                            "Bearer $token"
                        )
                )
                    .andExpect(status().isOk)
            }
        }
        `when`("토큰이 헤더에 없는 경우") {
            then("예외 발생") {
                shouldThrow<ServletException> { mockMvc.perform(get("/test")) }
            }
        }
        `when`("올바르지 않은 토큰을 사용하는 경우") {
            then("예외 발생") {
                shouldThrow<ServletException> {
                    mockMvc.perform(get("/test").header("Authorization", "Bearer"))
                }
            }
        }
    }
})