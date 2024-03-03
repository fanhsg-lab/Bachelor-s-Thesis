package com.example.ptyxiakh3

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<Integer> {
        val listType = object : TypeToken<List<Integer>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Integer>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}




class StringListTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",") // Assuming you separate module names with commas
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        return value?.joinToString(",")
    }

}
class LongListTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<Long>? {
        val listType = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Long>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}


