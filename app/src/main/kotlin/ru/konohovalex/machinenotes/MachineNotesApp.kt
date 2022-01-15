package ru.konohovalex.machinenotes

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import ru.konohovalex.core.data.LanguageCodes
import ru.konohovalex.core.utils.changeLocale
import java.util.Locale

@HiltAndroidApp
internal class MachineNotesApp : Application() {
    // tbd place SharedPreferences of language in Preferences feature and make dynamic changes with Compose
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.changeLocale(Locale(LanguageCodes.ENGLISH)))
    }
}
