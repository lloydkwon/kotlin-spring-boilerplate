package boilerplate.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class SampleApplication

fun main(args: Array<String>) {
	runApplication<SampleApplication>(*args)
}
