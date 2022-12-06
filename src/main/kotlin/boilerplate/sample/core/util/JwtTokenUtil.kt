package boilerplate.sample.core.util

import boilerplate.sample.core.interceptor.TokenPayload
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtTokenUtil(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,

    @Value("\${jwt.algorithm}")
    private val algorithm: String,
) {
    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun decode(token: String): TokenPayload {
        if (token.isEmpty()) {
            throw JwtTokenDecodeException()
        }

        try {
            val claims = getClaims(token)
            return TokenPayload(
                userId = claims["user_id"].toString().toLong()
            )
        } catch (e: Exception) {
            throw JwtTokenDecodeException()
        }
    }
}
