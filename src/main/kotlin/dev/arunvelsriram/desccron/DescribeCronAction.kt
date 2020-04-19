package dev.arunvelsriram.desccron

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor


class DescribeCronAction : AnAction() {
    private val cronDescriptor = service<CronDescriptor>()
    private val hintManager = service<HintManager>()

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

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        try {
            val description = cronDescriptor.describe(getSelectedText(editor))
            hintManager.showInformationHint(editor!!, description)
        } catch (e: IllegalArgumentException) {
            hintManager.showErrorHint(editor!!, e.message ?: "Failed to describe cron")
        }
    }

    private fun getSelectedText(editor: Editor?) = editor?.selectionModel?.selectedText?.trim() ?: ""
}
