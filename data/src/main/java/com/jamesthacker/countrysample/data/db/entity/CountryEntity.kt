package com.jamesthacker.countrysample.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jamesthacker.countrysample.domain.model.Country
import java.time.Instant

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun isRecentlyUpdated(): Boolean {
        return Instant.ofEpochMilli(updatedAt)
            .plusMillis(CACHE_LIFETIME_MS)
            .isAfter(Instant.now())
    }

    companion object {
        private const val CACHE_LIFETIME_MS = 60 * 60 * 1000L // 1 hour
    }
}

fun Country.toEntity(): CountryEntity {
    return CountryEntity(
        name,
    )
}
