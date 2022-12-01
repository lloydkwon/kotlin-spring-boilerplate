package boilerplate.sample.core.healthcheck

import io.kotest.core.spec.style.ExpectSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class HealthCheckControllerTest(
    @Autowired private val mockMvc: MockMvc
) : ExpectSpec({

    context("HealthCheckController") {
        expect("헬스체크 요청이 정상 동작한다") {
            mockMvc.perform(MockMvcRequestBuilders.get("/health_check"))
                .andExpect(MockMvcResultMatchers.status().isOk)
        }
    }
})
