package dev.arunvelsriram.desccron

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MenuItemActionTest {
    @MockK
    lateinit var actionEvent: AnActionEvent

    @MockK
    lateinit var editor: Editor

    @MockK
    lateinit var selectionModel: SelectionModel

    @MockK
    lateinit var hintManager: HintManager

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)

        mockkStatic(HintManager::class)
        every {
            HintManager.getInstance()
        } returns hintManager
    }

    @AfterEach
    internal fun tearDown() {
        unmockkStatic(HintManager::class)
    }

    @Test
    fun `should return empty editor is null`() {
        val action = menuItemActionImpl()

        val actual = action.getSelectedText(null)

        assertEquals("", actual)
    }

    @Test
    fun `should return empty when selection is null`() {
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns null
        val action = menuItemActionImpl()

        val actual = action.getSelectedText(editor)

        assertEquals("", actual)
    }

    @Test
    fun `should return empty when selection is white spaces`() {
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns "     "
        val action = menuItemActionImpl()

        val actual = action.getSelectedText(editor)

        assertEquals("", actual)
    }

    @Test
    fun `should return selected text`() {
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns "cron"
        val action = menuItemActionImpl()

        val actual = action.getSelectedText(editor)

        assertEquals("cron", actual)
    }

    @Test
    fun `should disable and hide when selection is empty`() {
        every { actionEvent.getData(PlatformDataKeys.EDITOR) } returns editor
        every { editor.selectionModel } returns selectionModel
        every { selectionModel.selectedText } returns ""
        every { actionEvent.presentation.isEnabled = false } just runs
        every { actionEvent.presentation.isVisible = false } just runs
        val action = menuItemActionImpl()

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
        val action = menuItemActionImpl()

        action.update(actionEvent)

        verify { actionEvent.presentation.isEnabled = true }
        verify { actionEvent.presentation.isVisible = true }
    }

    private fun menuItemActionImpl(): MenuItemAction {
        return object : MenuItemAction() {
            override fun actionPerformed(e: AnActionEvent) {}
        }
    }
}