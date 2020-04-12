package dev.arunvelsriram.descron

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
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

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
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
}