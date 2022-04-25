package dev.arunvelsriram.desccron

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DescribeCronActionTest {
    @MockK
    lateinit var actionEvent: AnActionEvent

    @MockK
    lateinit var editor: Editor

    @MockK
    lateinit var cronDescriptorService: CronDescriptorService

    @MockK
    lateinit var hintManager: HintManager

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)

        mockkObject(CronDescriptorService.Companion)
        every {
            CronDescriptorService.getInstance()
        } returns cronDescriptorService

        mockkStatic(HintManager::class)
        every {
            HintManager.getInstance()
        } returns hintManager
    }

    @AfterEach
    internal fun tearDown() {
        unmockkObject(CronDescriptorService.Companion)
        unmockkStatic(HintManager::class)
    }

    @Test
    fun `should show cron description`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "* * * * *"
        every { cronDescriptorService.describe("* * * * *") } returns "every minute"
        every { hintManager.showInformationHint(editor, "every minute") } just runs
        val action = DescribeCronAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.describe("* * * * *") }
        verify { hintManager.showInformationHint(editor, "every minute") }
    }

    @Test
    fun `should handle exception and show default error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptorService.describe("invalid") } throws IllegalArgumentException()
        every { hintManager.showErrorHint(editor, "Failed to describe cron") } just runs
        val action = DescribeCronAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.describe("invalid") }
        verify { hintManager.showErrorHint(editor, "Failed to describe cron") }
    }

    @Test
    fun `should handle exception and show error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptorService.describe("invalid") } throws IllegalArgumentException("failed to describe")
        every { hintManager.showErrorHint(editor, "failed to describe") } just runs
        val action = DescribeCronAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.describe("invalid") }
        verify { hintManager.showErrorHint(editor, "failed to describe") }
    }
}
