package com.weappsinc.watertracker.app.core.constants

import androidx.annotation.StringRes
import com.weappsinc.watertracker.R

/** 12 ngôn ngữ mockup: BCP 47 tag + file cờ trong assets/flags. */
data class AppLanguageOption(
    val localeTag: String,
    val flagAssetFile: String,
    @param:StringRes val labelRes: Int,
)

object AppLanguageCatalog {
    val options: List<AppLanguageOption> = listOf(
        AppLanguageOption("en-GB", "gb.png", R.string.lang_en_gb),
        AppLanguageOption("en-US", "us.png", R.string.lang_en_us),
        AppLanguageOption("pt-BR", "br.png", R.string.lang_pt_br),
        AppLanguageOption("es", "es.png", R.string.lang_es),
        AppLanguageOption("ja", "jp.png", R.string.lang_ja),
        AppLanguageOption("ko", "kr.png", R.string.lang_ko),
        AppLanguageOption("de", "de.png", R.string.lang_de),
        AppLanguageOption("id", "id.png", R.string.lang_id),
        AppLanguageOption("hi", "in.png", R.string.lang_hi),
        AppLanguageOption("ar", "sa.png", R.string.lang_ar),
        AppLanguageOption("vi", "vn.png", R.string.lang_vi),
        AppLanguageOption("tr", "tr.png", R.string.lang_tr),
    )
}
