package com.weappsinc.watertracker.app.core.local

import androidx.core.os.LocaleListCompat
import com.weappsinc.watertracker.app.core.constants.AppLanguageCatalog
import java.util.Locale

/** Map LocaleList hệ thống → tag trong [AppLanguageCatalog]; không khớp thì fallback en-US. */
object SystemLocaleResolver {
    fun matchedCatalogTag(): String {
        val supported = AppLanguageCatalog.options.map { it.localeTag }
        val list = LocaleListCompat.getDefault()
        repeat(list.size()) { i ->
            val loc = list.get(i) ?: return@repeat
            match(locale = loc, supported = supported)?.let { return it }
        }
        return AppLocalePreferences.DEFAULT_LOCALE_TAG
    }

    private fun normTag(tag: String): String = tag.replace('_', '-').lowercase(Locale.ROOT)

    private fun match(locale: Locale, supported: List<String>): String? {
        val lang = locale.language.lowercase(Locale.ROOT)
        if (lang.isEmpty()) return null

        val sysNorm = normTag(locale.toLanguageTag())
        supported.firstOrNull { normTag(it) == sysNorm }?.let { return it }

        if (lang == "pt") {
            val cc = locale.country.uppercase(Locale.ROOT)
            return if (cc == "BR") "pt-BR" else supported.find { normTag(it).startsWith("pt-") } ?: "pt-BR"
        }
        if (lang == "in" || lang == "id") {
            return supported.find { it.equals("id", ignoreCase = true) }
        }

        var bestOnlyLang: String? = null
        for (tag in supported) {
            val cur = Locale.forLanguageTag(tag)
            if (cur.language.isEmpty()) continue
            if (!cur.language.equals(lang, ignoreCase = true)) continue
            if (locale.country.isNotEmpty() &&
                locale.country.uppercase(Locale.ROOT) == cur.country.uppercase(Locale.ROOT)
            ) {
                return tag
            }
            if (bestOnlyLang == null) bestOnlyLang = tag
        }
        if (lang == "en") {
            val cc = locale.country.uppercase(Locale.ROOT)
            return when {
                cc == "GB" || cc == "UK" -> supported.find { it.equals("en-GB", ignoreCase = true) } ?: bestOnlyLang
                else -> supported.find { it.equals("en-US", ignoreCase = true) } ?: bestOnlyLang
            }
        }
        return bestOnlyLang
    }
}
