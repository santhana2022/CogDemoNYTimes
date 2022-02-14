package com.cognizant.caponeteambuild.repository.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.cognizant.caponeteambuild.data.Multimedia
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromStringsListToString(value : List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun fromStringToStringsList(value: String): List<String> = Json.decodeFromString(value)

    @TypeConverter
    fun fromMultimediaListToString(list : List<Multimedia>?): String  = Json.encodeToString(list)

    @TypeConverter
    fun fromStringToMultimediaList(value: String): List<Multimedia>? = Json.decodeFromString(value)
}