package com.example.mobile_cw_2

import androidx.room.*

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<Meals>

    @Query("SELECT * FROM meals WHERE meal_name LIKE :mealName")
    suspend fun findMealByName(mealName: String): List<Meals>

    @Query("SELECT meal_name FROM meals")
    suspend fun getAllMealNames(): List<String>

    //get category by meal name
    @Query("SELECT category FROM meals WHERE meal_name LIKE :mealName")
    suspend fun getCategoryByMealName(mealName: String): String

    //get area by meal name
    @Query("SELECT area FROM meals WHERE meal_name LIKE :mealName")
    suspend fun getAreaByMealName(mealName: String): String

    //query to get all the details of all meals
    @Query("SELECT * FROM meals")
    suspend fun getAllMealDetails(): List<Meals>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(vararg meal: Meals)

    @Delete
    suspend fun deleteMeal(meal: Meals)

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}