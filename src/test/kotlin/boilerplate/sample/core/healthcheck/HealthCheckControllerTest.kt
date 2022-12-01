package boilerplate.sample.core.healthcheck

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class HealthCheckControllerTest(
    @Autowired private val mockMvc: MockMvc
) : BehaviorSpec({

    given("HealthCheckController") {
        `when`("요청이") {
            then("정상동작한다") {
                mockMvc.perform(get("/health_check"))
                    .andExpect(status().isOk)
            }
        }
    }
})