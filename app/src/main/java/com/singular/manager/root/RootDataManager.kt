package com.singular.manager.root

import com.singular.manager.domain.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class RootDataManager {

    private val GAID_PATHS = listOf(
        "/data/data/com.google.android.gms/shared_prefs/adid_settings.xml",
        "/data/data/com.google.android.gms/shared_prefs/advertising_id.xml",
        "/data/data/com.android.vending/shared_prefs/finsky_preferences.xml"
    )

    suspend fun extractGaid(): String? = withContext(Dispatchers.IO) {
        if (!RootUtils.isRooted()) return@withContext null

        for (path in GAID_PATHS) {
            val content = RootUtils.readFile(path)
            if (content != null) {
                val gaid = parseGaidFromXml(content)
                if (gaid != null) return@withContext gaid
            }
        }
        return@withContext null
    }

    private fun parseGaidFromXml(xmlContent: String): String? {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(xmlContent.reader())

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "string") {
                    val name = parser.getAttributeValue(null, "name")
                    if (name == "advertising_id" || name == "adid") {
                        parser.next()
                        return parser.text
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun buildProfileFromDevice(): Profile? = withContext(Dispatchers.IO) {
        val gaid = extractGaid()
        if (gaid != null) {
            return@withContext Profile(
                name = "Device Profile - ${System.currentTimeMillis()}",
                gaid = gaid,
                email = null,
                uid = null,
                createdAt = System.currentTimeMillis()
            )
        }
        return@withContext null
    }
}
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
