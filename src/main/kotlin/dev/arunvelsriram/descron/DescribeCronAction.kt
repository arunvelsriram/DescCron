package dev.arunvelsriram.descron

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys


class DescribeCronAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val selectedText = editor?.selectionModel?.selectedText?.trim() ?: ""
        if (selectedText.isEmpty()) {
            e.presentation.isEnabled = false
            e.presentation.isVisible = false
        } else {
            e.presentation.isVisible = true
            e.presentation.isEnabled = true
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
