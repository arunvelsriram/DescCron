package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnActionEvent

class PreviousRunAction : MenuItemAction() {
    private val cronDescriptorService = CronDescriptorService.getInstance()

    override fun actionPerformed(e: AnActionEvent) {
        val action = { s: String -> cronDescriptorService.previousRun(s) }
        perform(e, action, "Failed to get previous run")
    }
}
