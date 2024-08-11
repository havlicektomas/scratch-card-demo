package dev.havlicektomas.scratchcard.common.data.compute

import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class ScratchCardCodeProviderImpl: ScratchCardCodeProvider {

    override suspend fun computeCode(): String {
        delay(2.seconds)
        return UUID.randomUUID().toString()
    }
}