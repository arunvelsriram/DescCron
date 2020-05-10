package dev.arunvelsriram.desccron

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service

class DescribeCronAction : MenuItemAction() {
    private val cronDescriptor = service<CronDescriptor>()

    override fun actionPerformed(e: AnActionEvent) {
        val action = { s: String -> cronDescriptor.describe(s) }
        perform(e, action, "Failed to describe cron")
    }
}
