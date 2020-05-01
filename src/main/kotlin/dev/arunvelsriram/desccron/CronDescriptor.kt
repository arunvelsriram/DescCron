package dev.arunvelsriram.desccron

import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import com.intellij.openapi.components.Service
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class CronDescriptor {
    private val parser = CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX))
    private val locale = Locale.getDefault()
    private val descriptor = CronDescriptor.instance(locale)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", locale)

    fun describe(expr: String): String {
        val cron = parser.parse(expr)
        return descriptor.describe(cron)
    }

    fun nextRun(expr: String): String {
        val cron = parser.parse(expr)
        val executionTime = ExecutionTime.forCron(cron)
        val now = ZonedDateTime.now()
        val nextExecution = executionTime.nextExecution(now)
        return nextExecution.get().format(formatter)
    }
}
