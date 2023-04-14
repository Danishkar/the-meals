package com.example.mobile_cw_2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meals :: class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealsDao():MealsDao
}