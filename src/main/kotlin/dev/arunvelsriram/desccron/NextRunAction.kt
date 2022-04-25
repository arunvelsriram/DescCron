package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service

class NextRunAction : MenuItemAction() {
    private val cronDescriptorService = CronDescriptorService.getInstance()

    override fun actionPerformed(e: AnActionEvent) {
        val action = { s: String -> cronDescriptorService.nextRun(s) }
        perform(e, action, "Failed to get next run")
    }
}
