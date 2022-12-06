package boilerplate.sample

import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestContextManager

class SampleApplicationTests : FunSpec() {
    init {
        val testContextManager = TestContextManager(this::class.java)
        test("Context should load")
        testContextManager.beforeTestClass()
        testContextManager.prepareTestInstance(this)
    }
}
