package com.singular.manager.root

import com.singular.manager.domain.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParserFactory

class RootDataManager {

    // Common paths where GAID might be stored
    private val GAID_PATHS = listOf(
        "/data/data/com.google.android.gms/shared_prefs/adid_settings.xml",
        "/data/data/com.google.android.gms/shared_prefs/advertising_id.xml",
        "/data/data/com.android.vending/shared_prefs/finsky_preferences.xml"
    )

    suspend fun extractGaid(): String? = withContext(Dispatchers.IO) {
        if (!RootUtils.isRooted()) {
            println("[ERROR] Device is not rooted. Cannot extract GAID.")
            return@withContext null
        }

        for (path in GAID_PATHS) {
            val content = RootUtils.readFile(path)
            if (content != null) {
                val gaid = parseGaidFromXml(content)
                if (gaid != null) {
                    println("[DEBUG] GAID extracted from $path: $gaid")
                    return@withContext gaid
                }
            }
        }
        println("[DEBUG] GAID not found in common paths.")
        return@withContext null
    }

    private fun parseGaidFromXml(xmlContent: String): String? {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(xmlContent.reader())

            var eventType = parser.eventType
            var gaid: String? = null

            while (eventType != parser.END_DOCUMENT) {
                if (eventType == parser.START_TAG && parser.name == "string") {
                    val name = parser.getAttributeValue(null, "name")
                    if (name == "advertising_id" || name == "adid") {
                        parser.next()
                        gaid = parser.text
                        break
                    }
                }
                eventType = parser.next()
            }
            return gaid
        } catch (e: Exception) {
            println("[ERROR] Error parsing XML for GAID: ${e.message}")
            return null
        }
    }

    // Placeholder for other data extraction logic (e.g., email, UID if found in system files)
    suspend fun extractOtherProfileData(): Map<String, String> = withContext(Dispatchers.IO) {
        val data = mutableMapOf<String, String>()
        // Implement logic to find email/UID in other system files if applicable
        // For now, this is a placeholder.
        return@withContext data
    }

    suspend fun buildProfileFromDevice(): Profile? = withContext(Dispatchers.IO) {
        val gaid = extractGaid()
        val otherData = extractOtherProfileData()

        if (gaid != null) {
            // For now, name can be a generic device name or timestamp
            // In a real app, you might prompt the user for a name
            return@withContext Profile(
                name = "Device Profile - ${System.currentTimeMillis()}",
                gaid = gaid,
                email = otherData["email"],
                uid = otherData["uid"],
                createdAt = System.currentTimeMillis()
            )
        }
        return@withContext null
    }
}
