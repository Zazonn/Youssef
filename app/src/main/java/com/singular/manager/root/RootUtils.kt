package com.singular.manager.root

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RootUtils {

    init {
        // Initialize libsu Shell
        Shell.setDefaultBuilder(Shell.Builder.create()
            .setFlags(Shell.FLAG_REDIRECT_STDERR)
            .setTimeout(10)
        )
    }

    suspend fun isRooted(): Boolean = withContext(Dispatchers.IO) {
        Shell.rootAccess()
    }

    suspend fun executeCommand(command: String): List<String> = withContext(Dispatchers.IO) {
        val result = Shell.cmd(command).exec()
        if (result.isSuccess && result.out.isNotEmpty()) {
            result.out
        } else {
            emptyList()
        }
    }

    suspend fun readFile(filePath: String): String? = withContext(Dispatchers.IO) {
        val command = "cat $filePath"
        val result = executeCommand(command)
        result.firstOrNull()
    }
}
