package dev.arunvelsriram.desccron

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.ZoneId
import java.time.ZonedDateTime

class CronDescriptorServiceTest {
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
        val d = CronDescriptorService()

        assertEquals("every minute", d.describe("* * * * *"))
    }

    @Test
    fun `should throw exception for invalid cron`() {
        val d = CronDescriptorService()

        assertThrows<IllegalArgumentException> { d.describe("a b c d e") }
    }

    @Test
    fun `should get the next run`() {
        val d = CronDescriptorService()

        assertEquals("2020-05-01 14:30:00 IST", d.nextRun("30 */2 * * *"))
    }

    @Test
    fun `should get the previous run`() {
        val d = CronDescriptorService()

        assertEquals("2020-05-01 12:30:00 IST", d.previousRun("30 */2 * * *"))
    }
}
