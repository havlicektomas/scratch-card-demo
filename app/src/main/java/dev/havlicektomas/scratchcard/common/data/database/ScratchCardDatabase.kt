package dev.havlicektomas.scratchcard.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScratchCardEntity::class], version = 1)
abstract class ScratchCardDatabase : RoomDatabase() {
    abstract fun cardDao(): ScratchCardDao
}