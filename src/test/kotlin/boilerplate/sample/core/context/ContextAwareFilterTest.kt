package boilerplate.sample.core.context

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebMvcTest
class ContextAwareFilterTest : BehaviorSpec({
    @RestController
    class TestController {
        @GetMapping("/test")
        fun test(): String {
            return RequestContextStore.get().correlationId
        }
    }

    given("ContextAwareFilter") {
        val testController = TestController()
        val contextAwareFilter = ContextAwareFilter()
        val mockMvc = MockMvcBuilders.standaloneSetup(testController)
            .addFilter<StandaloneMockMvcBuilder>(contextAwareFilter)
            .build()

        `when`("X-Correlation-Id 헤더에 값을 넣어서 요청하면") {
            then("정상적으로 적용된다") {
                mockMvc.get("/test") {
                    header(name = "X-Correlation-Id", "Test")
                }
                    .andExpect {
                        status { isOk() }
                        content { string("Test") }
                    }
            }
        }
    }
})