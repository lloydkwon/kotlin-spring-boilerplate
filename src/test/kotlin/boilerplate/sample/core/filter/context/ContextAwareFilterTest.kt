package boilerplate.sample.core.filter.context

import boilerplate.sample.core.filter.context.ContextAwareFilter
import boilerplate.sample.core.filter.context.RequestContextStore
import boilerplate.sample.core.util.JwtTokenUtil
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {
    @GetMapping("/test")
    fun test(): String {
        return RequestContextStore.get().correlationId
    }
}

@WebMvcTest(TestController::class)
class ContextAwareFilterTest(
    @MockkBean private val jpaMetamodelMappingContext: JpaMetamodelMappingContext,
    @MockkBean private val jwtTokenUtil: JwtTokenUtil,
) : BehaviorSpec({
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
