package net.inherency.flyio

import java.io.File

class Env {
    companion object {
        private fun readFromEnv(configKey: String): String {
            val value = System.getenv()[configKey]
            if (value != null && value != "file") {
                return value
            } else {
                val fromFile = readFromFile(configKey)
                if (fromFile != null) {
                    return fromFile
                }
                throw RuntimeException("Could not find config: $configKey")
            }
        }

        private fun readFromFile(configKey: String): String? {
            return if (configKey == "googleAuthJson") {
                println("Reading google auth from file")
                val fileLocation: String = GOOGLE_AUTH_JSON_FILE_LOCATION
                val fileContents = File(fileLocation).readText(Charsets.UTF_8)
                fileContents
            } else {
                null
            }
        }

        val GOOGLE_AUTH_JSON_FILE_LOCATION = readFromEnv("googleAuthJsonLocation")
        val GOOGLE_AUTH_JSON = readFromEnv("googleAuthJson")
    }
}