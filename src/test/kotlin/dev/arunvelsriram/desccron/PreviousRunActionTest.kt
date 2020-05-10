package dev.arunvelsriram.desccron

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.DateTimeException

internal class PreviousRunActionTest {
    @MockK
    lateinit var actionEvent: AnActionEvent

    @MockK
    lateinit var editor: Editor

    @MockK
    lateinit var cronDescriptor: CronDescriptor

    @MockK
    lateinit var hintManager: HintManager

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(ApplicationManager::class)
        every {
            ApplicationManager.getApplication().getService(CronDescriptor::class.java, any())
        } returns cronDescriptor
        every {
            ApplicationManager.getApplication().getService(HintManager::class.java, any())
        } returns hintManager
    }

    @Test
    fun `should show previous run`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "* * * * *"
        every { cronDescriptor.previousRun("* * * * *") } returns "2020-05-01 13:00:00 IST"
        every { hintManager.showInformationHint(editor, "2020-05-01 13:00:00 IST") } just runs
        val action = PreviousRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptor.previousRun("* * * * *") }
        verify { hintManager.showInformationHint(editor, "2020-05-01 13:00:00 IST") }
    }

    @Test
    fun `should catch exception and show default error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptor.previousRun("invalid") } throws IllegalArgumentException()
        every { hintManager.showErrorHint(editor, "Failed to get previous run") } just runs
        val action = PreviousRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptor.previousRun("invalid") }
        verify { hintManager.showErrorHint(editor, "Failed to get previous run") }
    }

    @Test
    fun `should catch exception and show error message`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptor.previousRun("invalid") } throws DateTimeException("some error")
        every { hintManager.showErrorHint(editor, "some error") } just runs
        val action = PreviousRunAction()

        action.actionPerformed(actionEvent)

        verify { cronDescriptor.previousRun("invalid") }
        verify { hintManager.showErrorHint(editor, "some error") }
    }
}
