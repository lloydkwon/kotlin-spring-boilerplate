package boilerplate.sample.core.filter.log

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.ByteArrayInputStream
import java.io.InputStream

class RequestLoggingFilterTest : BehaviorSpec({
    given("RequestLoggingFilter") {
        val requestMock = mockk<ContentCachingRequestWrapper>()
        val responseMock = mockk<ContentCachingResponseWrapper>()

        `when`("Request Body가 없는 경우") {
            every { requestMock.contentAsByteArray } answers { byteArrayOf(0) }

            then("getRequestBody()는 ''를 리턴한다") {
                val result = RequestLoggingFilter.getRequestBody(requestMock)
                result.length shouldBe 1
            }
        }
        `when`("Request Body가 abc인 경우") {
            every { requestMock.contentAsByteArray } answers {
                byteArrayOf(97, 98, 99)
            }

            then("getRequestBody()는 'abc'을 리턴한다") {
                val result = RequestLoggingFilter.getRequestBody(requestMock)
                result shouldBe "abc"
            }
        }
        `when`("Response Body가 없는 경우") {
            every { responseMock.contentInputStream } answers { InputStream.nullInputStream() }
            every { responseMock.copyBodyToResponse() } answers { }

            then("getResponseBody()는 ''를 리턴한다") {
                val result = RequestLoggingFilter.getResponseBody(responseMock)
                result shouldBe ""
            }
        }
        `when`("Response Body가 abc인 경우") {
            every { responseMock.contentInputStream } answers {
                ByteArrayInputStream(
                    byteArrayOf(
                        97,
                        98,
                        99
                    )
                )
            }
            every { responseMock.copyBodyToResponse() } answers { }

            then("getResponseBody()는 'abc'를 리턴한다") {
                val result = RequestLoggingFilter.getResponseBody(responseMock)
                result shouldBe "abc"
            }
        }
    }
})
