package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor

abstract class MenuItemAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        if (getSelectedText(editor).isEmpty()) {
            e.presentation.isEnabled = false
            e.presentation.isVisible = false
        } else {
            e.presentation.isVisible = true
            e.presentation.isEnabled = true
        }
    }

    fun getSelectedText(editor: Editor?) = editor?.selectionModel?.selectedText?.trim() ?: ""
}
