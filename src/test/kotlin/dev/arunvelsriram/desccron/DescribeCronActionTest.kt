package dev.arunvelsriram.desccron

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DescribeCronActionTest {
    @MockK
    lateinit var actionEvent: AnActionEvent

    @MockK
    lateinit var editor: Editor

    @MockK
    lateinit var selectionModel: SelectionModel

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
    fun `should disable and hide when ide data is null`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns null
        every { actionEvent.presentation.isEnabled = false } just runs
        every { actionEvent.presentation.isVisible = false } just runs
        val action = DescribeCronAction()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = false }
        verify { actionEvent.presentation.isVisible = false }
    }


    @Test
    fun `should disable and hide when selection is null`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns null
        every { actionEvent.presentation.isEnabled = false } just runs
        every { actionEvent.presentation.isVisible = false } just runs
        val action = DescribeCronAction()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = false }
        verify { actionEvent.presentation.isVisible = false }
    }

    @Test
    fun `should disable and hide when selection is empty`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns ""
        every { actionEvent.presentation.isEnabled = false } just runs
        every { actionEvent.presentation.isVisible = false } just runs
        val action = DescribeCronAction()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = false }
        verify { actionEvent.presentation.isVisible = false }
    }

    @Test
    fun `should disable and hide when selection is white spaces`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns "     "
        every { actionEvent.presentation.isEnabled = false } just runs
        every { actionEvent.presentation.isVisible = false } just runs
        val action = DescribeCronAction()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = false }
        verify { actionEvent.presentation.isVisible = false }
    }

    @Test
    fun `should enable and show when selection is not empty`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns "cron"
        every { actionEvent.presentation.isEnabled = true } just runs
        every { actionEvent.presentation.isVisible = true } just runs
        val action = DescribeCronAction()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = true }
        verify { actionEvent.presentation.isVisible = true }
    }

    @Test
    fun `should show cron description as info hint`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "* * * * *"
        every { cronDescriptor.describe("* * * * *") } returns "every minute"
        every { hintManager.showInformationHint(editor, "every minute") } just runs
        val action = DescribeCronAction()

        action.actionPerformed(actionEvent)
        verify { cronDescriptor.describe("* * * * *") }
        verify { hintManager.showInformationHint(editor, "every minute") }
    }

    @Test
    fun `should show error message as error hint`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel.selectedText } returns "invalid"
        every { cronDescriptor.describe("invalid") } throws IllegalArgumentException("failed to describe")
        every { hintManager.showErrorHint(editor, "failed to describe") } just runs
        val action = DescribeCronAction()

        action.actionPerformed(actionEvent)
        verify { cronDescriptor.describe("invalid") }
        verify { hintManager.showErrorHint(editor, "failed to describe") }
    }
}
