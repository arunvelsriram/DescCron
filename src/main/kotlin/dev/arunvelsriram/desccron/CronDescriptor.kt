package dev.arunvelsriram.desccron

import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.parser.CronParser
import com.intellij.openapi.components.Service
import java.util.*

@Service
class CronDescriptor {
    private val parser = CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX))
    private val descriptor = CronDescriptor.instance(Locale.getDefault())

    fun describe(expr: String): String {
        val cron = parser.parse(expr)
        return descriptor.describe(cron)
    }
}
