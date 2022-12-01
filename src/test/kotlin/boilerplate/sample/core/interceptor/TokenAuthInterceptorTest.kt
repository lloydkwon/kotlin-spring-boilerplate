package boilerplate.sample.core.interceptor

import boilerplate.sample.core.util.JwtTokenUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import jakarta.servlet.ServletException
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebMvcTest
class TokenAuthInterceptorTest : ExpectSpec({
    @RestController
    class TestController {
        @GetMapping("/test")
        fun test(): ResponseEntity<Any> {
            return ResponseEntity.ok().build()
        }
    }

    context("TokenAuthInterceptor") {
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

        expect("올바른 토큰인 경우 요청이 정상 동작한다") {
            val token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxfQ.t-dL6resmtNGqurhA94e3BuBmm00PB8aooh9EmXCI-A"
            mockMvc.get("/test") {
                header(name = "Authorization", "Bearer $token")
            }
                .andExpect { status { isOk() } }
        }
        expect("토큰이 헤더에 없는 경우 예외가 발생한다") {
            shouldThrow<ServletException> { mockMvc.get("/test") }
        }
        expect("올바르지 않은 토큰인 경우 예외가 발생한다") {
            shouldThrow<ServletException> {
                mockMvc.get("/test") {
                    header(name = "Authorization", "")
                }
            }
        }
    }
})
