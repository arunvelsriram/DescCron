package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnActionEvent

class DescribeCronAction : MenuItemAction() {
    private val cronDescriptorService = CronDescriptorService.getInstance()

    override fun actionPerformed(e: AnActionEvent) {
        val action = { s: String -> cronDescriptorService.describe(s) }
        perform(e, action, "Failed to describe cron")
    }
}
