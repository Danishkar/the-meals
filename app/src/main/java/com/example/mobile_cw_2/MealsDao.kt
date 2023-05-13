package com.example.mobile_cw_2

import androidx.room.*

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(vararg meal: Meals)

    @Query("SELECT * FROM meals WHERE meal_name LIKE '%' || :searchQuery || '%' OR ingredient1 LIKE '%' || :searchQuery || '%' OR ingredient2 LIKE '%' || :searchQuery || '%' OR ingredient3 LIKE '%' || :searchQuery || '%' OR ingredient4 LIKE '%' || :searchQuery || '%' OR ingredient5 LIKE '%' || :searchQuery || '%' OR ingredient6 LIKE '%' || :searchQuery || '%' OR ingredient7 LIKE '%' || :searchQuery || '%' OR ingredient8 LIKE '%' || :searchQuery || '%' OR ingredient9 LIKE '%' || :searchQuery || '%' OR ingredient10 LIKE '%' || :searchQuery || '%' OR ingredient11 LIKE '%' || :searchQuery || '%' OR ingredient12 LIKE '%' || :searchQuery || '%' OR ingredient13 LIKE '%' || :searchQuery || '%' OR ingredient14 LIKE '%' || :searchQuery || '%' OR ingredient15 LIKE '%' || :searchQuery || '%' OR ingredient16 LIKE '%' || :searchQuery || '%' OR ingredient17 LIKE '%' || :searchQuery || '%' OR ingredient18 LIKE '%' || :searchQuery || '%' OR ingredient19 LIKE '%' || :searchQuery || '%' OR ingredient20 LIKE '%' || :searchQuery || '%'")
    suspend fun searchMeals(searchQuery: String): List<Meals>
}