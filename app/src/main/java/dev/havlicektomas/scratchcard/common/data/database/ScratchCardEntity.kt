package dev.havlicektomas.scratchcard.common.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScratchCardEntity(
    @PrimaryKey var id: Int? = null,
    val code: String,
    val activated: Boolean,
    val activationError: String?
)
