package dev.arunvelsriram.desccron

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.test.assertEquals

class CronDescriptorTest {
    @BeforeEach
    internal fun setUp() {
        mockkStatic(ZonedDateTime::class)
        // 2020-05-01 13:10:00:00 IST
        val now = ZonedDateTime.of(
            2020, 5, 1, 13, 10, 0, 0,
            ZoneId.of(ZoneId.SHORT_IDS["IST"])
        )
        every { ZonedDateTime.now() } returns now
    }

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
    fun `should get the next run`() {
        val d = CronDescriptor()

        assertEquals("2020-05-01 14:30:00 IST", d.nextRun("30 */2 * * *"))
    }
}
