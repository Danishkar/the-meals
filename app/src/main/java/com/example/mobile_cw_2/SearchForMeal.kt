package com.example.mobile_cw_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*

class SearchForMeal : AppCompatActivity() {
    private lateinit var mealsDao: MealsDao
    lateinit var searchMealsTextBox:EditText
    lateinit var mealsFromDbTextBox:TextView
    var foundMeals = java.lang.StringBuilder()
    lateinit var errorMessage2:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meal)
        mealsDao = DatabaseSingleton.mealsDao
        searchMealsTextBox = findViewById(R.id.searchMealsTextBox)
        mealsFromDbTextBox = findViewById(R.id.mealsFromDbTextBox)
        errorMessage2 = findViewById(R.id.errorMessage2)
        //        retreving the variable state on orientation changes
        if (savedInstanceState != null) {
            val foundMeal = savedInstanceState.getString("foundMeals")
            foundMeals = StringBuilder(foundMeal)
            mealsFromDbTextBox.text = savedInstanceState.getString("mealsFromDbTextBox")
            errorMessage2.text = savedInstanceState.getString("errorMessage2")
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("foundMeals", foundMeals.toString())
        outState.putString("mealsFromDbTextBox",mealsFromDbTextBox.text.toString())
        outState.putString("errorMessage2",errorMessage2.text.toString())
    }

    fun containsInt(input: String): Boolean {
        val chars = input.toCharArray()
        for (c in chars) {
            if (c.isDigit()) {
                return true
            }
        }
        return false
    }
    fun searchButtonClicked(view: View) {
        errorMessage2.text = ""
        foundMeals.setLength(0)
        mealsFromDbTextBox.text = ""
        val searchQuery = searchMealsTextBox.text.toString()
        val userInputContainInt = containsInt(searchQuery)
        if (userInputContainInt){
            errorMessage2.text = "Input cannot contain Integer."
        }else{
            runBlocking {
                launch {
                    // run the code of the coroutine in a new thread
                    withContext(Dispatchers.IO) {
                        val meals = mealsDao.searchMeals(searchQuery)
                        if (meals.isNotEmpty()){
                            for (meal in meals) {
                                val mealName = meal.mealName
                                val drinkAlternative = meal.drinkAlternate
                                val category = meal.category
                                val area = meal.area
                                val instruction = meal.instructions
                                val slicedInstruction : String
                                if(instruction != null){
                                    slicedInstruction = instruction.substring(0,30)+"...."
                                }else{
                                    slicedInstruction = "null"
                                }
                                val tags = meal.tags
                                val youtube = meal.youtube
                                val mealThumb = meal.mealThumb
                                foundMeals.append(
                                    "\"Meal\":\"$mealName\",\n\"DrinkAlternate\":\"$drinkAlternative\",\n\"Category\":\"$category\"" +
                                            ",\n\"Area\":\"$area\",\n\"Instructions\":\"$slicedInstruction\",\n\"Tags\":\"$tags\",\n\"Youtube\":\"$youtube \""
                                )
                                foundMeals.append(",\n\"Ingredient1\":\"${meal.ingredient1}\"")
                                foundMeals.append(",\n\"Ingredient2\":\"${meal.ingredient2}\"")
                                foundMeals.append(",\n\"Ingredient3\":\"${meal.ingredient3}\"")
                                foundMeals.append(",\n\"Ingredient4\":\"${meal.ingredient4}\"")
                                foundMeals.append(",\n\"Ingredient5\":\"${meal.ingredient5}\"")
                                foundMeals.append(",\n\"Ingredient6\":\"${meal.ingredient6}\"")
                                foundMeals.append(",\n\"Ingredient7\":\"${meal.ingredient7}\"")
                                foundMeals.append(",\n\"Ingredient8\":\"${meal.ingredient8}\"")
                                foundMeals.append(",\n\"Ingredient9\":\"${meal.ingredient9}\"")
                                foundMeals.append(",\n\"Ingredient10\":\"${meal.ingredient10}\"")
                                foundMeals.append(",\n\"Ingredient11\":\"${meal.ingredient11}\"")
                                foundMeals.append(",\n\"Ingredient12\":\"${meal.ingredient12}\"")
                                foundMeals.append(",\n\"Ingredient13\":\"${meal.ingredient13}\"")
                                foundMeals.append(",\n\"Ingredient14\":\"${meal.ingredient14}\"")
                                foundMeals.append(",\n\"Ingredient15\":\"${meal.ingredient15}\"")
                                foundMeals.append(",\n\"Ingredient16\":\"${meal.ingredient16}\"")
                                foundMeals.append(",\n\"Ingredient17\":\"${meal.ingredient17}\"")
                                foundMeals.append(",\n\"Ingredient18\":\"${meal.ingredient18}\"")
                                foundMeals.append(",\n\"Ingredient19\":\"${meal.ingredient19}\"")
                                foundMeals.append(",\n\"Ingredient20\":\"${meal.ingredient20}\"")
                                foundMeals.append(",\n\"Measure1\":\"${meal.measure1}\"")
                                foundMeals.append(",\n\"Measure2\":\"${meal.measure2}\"")
                                foundMeals.append(",\n\"Measure3\":\"${meal.measure3}\"")
                                foundMeals.append(",\n\"Measure4\":\"${meal.measure4}\"")
                                foundMeals.append(",\n\"Measure5\":\"${meal.measure5}\"")
                                foundMeals.append(",\n\"Measure6\":\"${meal.measure6}\"")
                                foundMeals.append(",\n\"Measure7\":\"${meal.measure7}\"")
                                foundMeals.append(",\n\"Measure8\":\"${meal.measure8}\"")
                                foundMeals.append(",\n\"Measure9\":\"${meal.measure9}\"")
                                foundMeals.append(",\n\"Measure10\":\"${meal.measure10}\"")
                                foundMeals.append(",\n\"Measure11\":\"${meal.measure11}\"")
                                foundMeals.append(",\n\"Measure12\":\"${meal.measure12}\"")
                                foundMeals.append(",\n\"Measure13\":\"${meal.measure13}\"")
                                foundMeals.append(",\n\"Measure14\":\"${meal.measure14}\"")
                                foundMeals.append(",\n\"Measure15\":\"${meal.measure15}\"")
                                foundMeals.append(",\n\"Measure16\":\"${meal.measure16}\"")
                                foundMeals.append(",\n\"Measure17\":\"${meal.measure17}\"")
                                foundMeals.append(",\n\"Measure18\":\"${meal.measure18}\"")
                                foundMeals.append(",\n\"Measure19\":\"${meal.measure19}\"")
                                foundMeals.append(",\n\"Measure20\":\"${meal.measure20}\"")
                                foundMeals.append("\n\n")
                                Log.d("Printing", meal.mealName)
                            }
                        }else{
                            foundMeals.append("Not Found.Try Again")
                        }

                    }
                    runOnUiThread {
                        mealsFromDbTextBox.setText(foundMeals)
                    }
                }
            }
        }
    }

    fun backButtonClicked(view: View) {
        val mainIntent = Intent(this,MainActivity::class.java)
        startActivity(mainIntent)
    }
}