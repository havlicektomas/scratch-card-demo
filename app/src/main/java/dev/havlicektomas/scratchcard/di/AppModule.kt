package dev.havlicektomas.scratchcard.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.havlicektomas.scratchcard.common.data.ScratchCardRepoImpl
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardDao
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardDatabase
import dev.havlicektomas.scratchcard.common.data.remote.ScratchCardApi
import dev.havlicektomas.scratchcard.common.domain.ScratchCardRepo
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ScratchCardDatabase {
        return Room.databaseBuilder(
            appContext,
            ScratchCardDatabase::class.java,
            "card_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: ScratchCardDatabase) : ScratchCardDao{
        return database.cardDao()
    }

    @Provides
    @Singleton
    fun provideScratchCardApi(): ScratchCardApi {
        val networkJson = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl("https://api.o2.sk/")
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        cardDao: ScratchCardDao,
        cardApi: ScratchCardApi
    ): ScratchCardRepo {
        return ScratchCardRepoImpl(cardDao, cardApi)
    }
}