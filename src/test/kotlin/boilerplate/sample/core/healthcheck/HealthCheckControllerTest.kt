package boilerplate.sample.core.healthcheck

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

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(HealthCheckController::class)
class HealthCheckControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val jpaMetamodelMappingContext: JpaMetamodelMappingContext,
    @MockkBean private val jwtTokenUtil: JwtTokenUtil,
) : ExpectSpec({

    context("HealthCheckController") {
        expect("헬스체크 요청이 정상 동작한다") {
            mockMvc.get("/health_check")
                .andExpect { status { isOk() } }
        }
    }
})
