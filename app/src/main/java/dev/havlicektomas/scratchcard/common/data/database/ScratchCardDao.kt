package dev.havlicektomas.scratchcard.common.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScratchCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(cardEntity: ScratchCardEntity)

    @Query("DELETE FROM ScratchCardEntity")
    fun deleteCards()

    @Query("SELECT * FROM ScratchCardEntity")
    fun getCard(): Flow<List<ScratchCardEntity>>
}