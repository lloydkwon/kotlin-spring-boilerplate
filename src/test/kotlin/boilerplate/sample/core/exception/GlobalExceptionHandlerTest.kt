package boilerplate.sample.core.exception

import boilerplate.sample.core.interceptor.AuthenticationException
import io.kotest.core.spec.style.ExpectSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {
    @GetMapping("/test")
    fun test(): String {
        throw AuthenticationException()
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest(
    @Autowired private val mockMvc: MockMvc,
) : ExpectSpec({
    context("GlobalExceptionHandler") {
        expect("Business 예외가 발생한다") {
            mockMvc.get("/test")
                .andExpect {
                    status { isUnauthorized() }
                    jsonPath("error") { "AUTH__ERROR" }
                }
        }
    }
})
