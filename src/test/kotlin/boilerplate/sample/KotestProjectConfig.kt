package boilerplate.sample

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
