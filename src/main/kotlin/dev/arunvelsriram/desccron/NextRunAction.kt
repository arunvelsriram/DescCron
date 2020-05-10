package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service

class NextRunAction : MenuItemAction() {
    private val cronDescriptor = service<CronDescriptor>()

    override fun actionPerformed(e: AnActionEvent) {
        val action = { s: String -> cronDescriptor.nextRun(s) }
        perform(e, action, "Failed to get next run")
    }
}
