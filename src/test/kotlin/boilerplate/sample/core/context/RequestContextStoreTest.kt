package boilerplate.sample.core.context

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import java.lang.RuntimeException

@WebMvcTest
@AutoConfigureMockMvc
class RequestContextStoreTest : BehaviorSpec({
    class RequestContextTestRunnable(
        private val correlationId: String,
        private val sleep: Long,
        var result: String? = null,
    ) : Runnable {
        override fun run() {
            RequestContextStore.set(RequestContext(correlationId))
            try {
                Thread.sleep(sleep)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
            result = RequestContextStore.get().correlationId
            println(result)
            RequestContextStore.clear()
        }

    }

    given("ContextAwareFilter") {
        val test1 = RequestContextTestRunnable("test1", 500)
        val test2 = RequestContextTestRunnable("test2", 0)

        `when`("여러번 요청을 했을 때") {
            val thread1 = Thread(test1)
            val thread2 = Thread(test2)
            thread1.start()
            thread2.start()
            thread1.join()
            thread2.join()

            then("각 요청별로 Correlation-Id가 다르다") {
                test1.result shouldNotBe test2.result
            }
        }
        `when`("setRequestContext가 호출되지 않은 경우") {
            then("INITIALIZED 상태이다") {
                val result = RequestContextStore.get()
                result shouldBe RequestContextStore.INITIALIZED
            }
        }
    }
})