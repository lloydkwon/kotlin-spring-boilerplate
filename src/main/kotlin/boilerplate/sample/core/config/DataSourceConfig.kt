package boilerplate.sample.core.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DataSourceConfig {
    @Bean(name = ["writerDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    fun writerDateSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean(name = ["readerDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.reader")
    fun readerDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean(name = ["routingDataSource"])
    @DependsOn("writerDataSource", "readerDataSource")
    fun routingDataSource(
        @Qualifier("writerDataSource") writerDataSource: DataSource,
        @Qualifier("readerDataSource") readerDataSource: DataSource,
    ): DataSource {
        val routingDataSource = RoutingDataSource()
        val dataSourceMap = HashMap<Any, Any>()

        dataSourceMap[DataSourceType.WRITER] = writerDataSource
        dataSourceMap[DataSourceType.READER] = readerDataSource
        routingDataSource.setTargetDataSources(dataSourceMap)
        routingDataSource.setDefaultTargetDataSource(writerDataSource)

        return routingDataSource
    }

    @Primary
    @Bean(name = ["dataSource"])
    fun dataSource(
        @Qualifier("routingDataSource") routingDataSource: DataSource,
    ): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }
}
