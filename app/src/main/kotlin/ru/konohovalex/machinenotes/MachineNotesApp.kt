package ru.konohovalex.machinenotes

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import ru.konohovalex.core.presentation.utils.changeLocale
import java.util.Locale

@HiltAndroidApp
internal class MachineNotesApp : Application() {
    // tbd make dynamic changes
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.changeLocale(Locale("en")))
    }
}
