package dev.arunvelsriram.descron

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class CronDescriptorTest {
    @Test
    fun `should describe cron`() {
        val d = CronDescriptor()

        assertEquals("every minute", d.describe("* * * * *"))
    }

    @Test
    fun `should throw exception for invalid cron`() {
        val d = CronDescriptor()

        assertThrows<IllegalArgumentException> { d.describe("a b c d e") }
    }

    @Test
    fun `should throw exception for non-unix cron`() {
        val d = CronDescriptor()

        assertThrows<IllegalArgumentException> { d.describe("* * * * * * *") }
    }
}
