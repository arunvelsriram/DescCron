package dev.arunvelsriram.desccron

import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class CronDescriptorService {
    private val parser = CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX))
    private val locale = Locale.getDefault()
    private val descriptor = CronDescriptor.instance(locale)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", locale)

    companion object {
        @JvmStatic
        fun getInstance(): CronDescriptorService = service()
    }

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

    fun previousRun(expr: String): String {
        val cron = parser.parse(expr)
        val executionTime = ExecutionTime.forCron(cron)
        val now = ZonedDateTime.now()
        val nextExecution = executionTime.lastExecution(now)
        return nextExecution.get().format(formatter)
    }
}
