package boilerplate.sample.core.exception

import boilerplate.sample.core.interceptor.AuthenticationException
import boilerplate.sample.core.util.JwtTokenUtil
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.ExpectSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.restdocs.RestDocumentationExtension
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

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(TestController::class)
class GlobalExceptionHandlerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private var jpaMetamodelMappingContext: JpaMetamodelMappingContext,
    @MockkBean private var jwtTokenUtil: JwtTokenUtil,
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
