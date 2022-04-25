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
import java.time.DateTimeException

internal class NextRunActionTest {
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
    fun `should show next run`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "* * * * *"
        every { cronDescriptorService.nextRun("* * * * *") } returns "2020-05-01 13:00:00 IST"
        every { hintManager.showInformationHint(editor, "2020-05-01 13:00:00 IST") } just runs
        val action = NextRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.nextRun("* * * * *") }
        verify { hintManager.showInformationHint(editor, "2020-05-01 13:00:00 IST") }
    }

    @Test
    fun `should catch exception and show default error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptorService.nextRun("invalid") } throws IllegalArgumentException()
        every { hintManager.showErrorHint(editor, "Failed to get next run") } just runs
        val action = NextRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.nextRun("invalid") }
        verify { hintManager.showErrorHint(editor, "Failed to get next run") }
    }

    @Test
    fun `should catch exception and show error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptorService.nextRun("invalid") } throws DateTimeException("some error")
        every { hintManager.showErrorHint(editor, "some error") } just runs
        val action = NextRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptorService.nextRun("invalid") }
        verify { hintManager.showErrorHint(editor, "some error") }
    }
}
