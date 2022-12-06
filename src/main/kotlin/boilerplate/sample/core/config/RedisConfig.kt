package boilerplate.sample.core.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

private val logger = KotlinLogging.logger {}


@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}")
    private val host: String,

    @Value("\${spring.redis.port}")
    private val port: Int,
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Primary
    @Bean(name = ["redisCacheManager"])
    fun redisCacheManager(
        redisConnectionFactory: RedisConnectionFactory,
    ): CacheManager {
        logger.info { "Initialize redisCacheManager" }
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .withCacheConfiguration(
                "redis-cache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))
            )
            .build()
    }
}
