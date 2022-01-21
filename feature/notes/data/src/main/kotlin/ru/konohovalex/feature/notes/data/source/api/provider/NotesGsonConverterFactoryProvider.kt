package ru.konohovalex.feature.notes.data.source.api.provider

import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory
import ru.konohovalex.core.data.arch.provider.Provider
import javax.inject.Inject

internal class NotesGsonConverterFactoryProvider
@Inject constructor() : Provider<Gson, GsonConverterFactory> {
    override fun provide(providerParams: Gson): GsonConverterFactory = GsonConverterFactory.create(providerParams)
}