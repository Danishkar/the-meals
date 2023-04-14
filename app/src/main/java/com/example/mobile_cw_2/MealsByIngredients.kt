package com.example.mobile_cw_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MealsByIngredients : AppCompatActivity() {
    private lateinit var mealsDao: MealsDao
    lateinit var ingredientTextBox : EditText
    lateinit var mealsListTextBox :TextView
    var allMeals = java.lang.StringBuilder()
    var retrievedMealsList = mutableListOf<Meals>()
    var ingredientsList = mutableListOf<String?>()
    var measuresList = mutableListOf<String?>()
    private lateinit var saveMealsToDatabase:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals_by_ingredients)
        ingredientTextBox = findViewById(R.id.ingredientTextBox)
        mealsListTextBox = findViewById(R.id.mealsListTextBox)
        mealsDao = DatabaseSingleton.mealsDao
        saveMealsToDatabase = findViewById(R.id.saveMealsToDatabase)
        saveMealsToDatabase.isEnabled = false
    }

    fun retrieveMealsButtonClicked(view: View) {
        allMeals.setLength(0)
        measuresList.clear()
        ingredientsList.clear()
        retrievedMealsList.clear()
        // collecting all the JSON string
        var stb = StringBuilder()
        val url_string = "https://www.themealdb.com/api/json/v1/1/filter.php?i="+ingredientTextBox.text.toString()
        // creating a url object
        val url = URL(url_string)
        // casting to HTTP step 5
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        runBlocking {
            launch {
                // run the code of the coroutine in a new thread
                withContext(Dispatchers.IO) {
                    var bf = BufferedReader(InputStreamReader(con.inputStream))
                    var line: String? = bf.readLine()
                    while (line != null) {
                        stb.append(line + "\n")
                        line = bf.readLine()
                    }
                    // this contains the full JSON returned by the Web Service
                    val json = JSONObject(stb.toString())
                    var jsonArray: JSONArray = json.getJSONArray("meals")
                    for (i in 0..jsonArray.length() - 1) {
                        val meal: JSONObject = jsonArray[i] as JSONObject
                        val mealId = meal["idMeal"] as String
                        retrieveDataFromMealId(mealId)
                    }
                    runOnUiThread {
                        mealsListTextBox.setText(allMeals)
                        saveMealsToDatabase.isEnabled = true
                    }
                }
            }
        }
    }
    fun retrieveDataFromMealId(mealId: String){
        var stb = StringBuilder()
        val url_string = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId"
        // creating a url object
        val url = URL(url_string)
        // casting to HTTP step 5
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        runBlocking {
            launch {
                // run the code of the coroutine in a new thread
                withContext(Dispatchers.IO) {
                    var bf = BufferedReader(InputStreamReader(con.inputStream))
                    var line: String? = bf.readLine()
                    while (line != null) {
                        stb.append(line + "\n")
                        line = bf.readLine()
                    }
                    // this contains the full JSON returned by the Web Service
                    val json = JSONObject(stb.toString())

                    var jsonArray: JSONArray = json.getJSONArray("meals")
                    for (i in 0..jsonArray.length() - 1) {
                        val meal: JSONObject = jsonArray[i] as JSONObject
                        val mealName = meal["strMeal"] as String

                        val drinkAlternative = if (meal.isNull("strDrinkAlternate")) null else meal.getString("strDrinkAlternate")
                        val category = meal["strCategory"] as String?
                        val area = meal["strArea"] as String?
                        val instruction = meal["strInstructions"] as String?
                        val tags = if (meal.isNull("strTags")) null else meal.getString("strTags")
                        val youtube = meal["strYoutube"] as String?
                        val mealThumb = meal["strMealThumb"] as String?
                        allMeals.append("\"Meal\":\"$mealName\",\n\"DrinkAlternate\":\"$drinkAlternative\",\n\"Category\":\"$category\"" +
                                ",\n\"Area\":\"$area\",\n\"Instructions\":\"$instruction\",\n\"Tags\":\"$tags\",\n\"Youtube\":\"$youtube \"")
                        for(y in 0 .. 19){
                            if (meal["strIngredient"+(y+1)] != ""){
                                val num = y+1
                                val ingredient = meal["strIngredient"+(y+1)]
                                allMeals.append(",\n\"Ingredient$num\":\"$ingredient\"")
                            }
                            ingredientsList.add(if (meal.isNull("strIngredient"+(y+1))) null else meal.getString("strIngredient"+(y+1)))

                        }
                        for(y in 0 .. 19){
                            if (meal["strMeasure"+(y+1)] != ""){
                                val num = y+1
                                val measure =  meal["strMeasure"+(y+1)]
                                allMeals.append(",\n\"Measure$num\":\"$measure\"")
                            }
                            measuresList.add(if (meal.isNull("strMeasure"+(y+1))) null else meal.getString("strMeasure"+(y+1)))
                        }
                        allMeals.append("\n\n")
//                        Log.d("MyNames", ingredientsList[i].toString())
                        retrievedMealsList.add(Meals(mealId, mealName,drinkAlternative,category,area,instruction,
                        mealThumb,tags,youtube,ingredientsList[0],ingredientsList[1],ingredientsList[2],ingredientsList[3]
                        ,ingredientsList[4],ingredientsList[5],ingredientsList[6],ingredientsList[7],ingredientsList[8],ingredientsList[9]
                        ,ingredientsList[10],ingredientsList[11],ingredientsList[12],ingredientsList[13],ingredientsList[14],ingredientsList[15]
                        ,ingredientsList[16],ingredientsList[17],ingredientsList[18],ingredientsList[19]
                        ,measuresList[0],measuresList[1],measuresList[2],measuresList[3],measuresList[4],measuresList[5],measuresList[6]
                        ,measuresList[7],measuresList[8],measuresList[9],measuresList[10],measuresList[11],measuresList[12],measuresList[13]
                        ,measuresList[14],measuresList[15],measuresList[16],measuresList[17],measuresList[18],measuresList[19]))
                        ingredientsList.clear()
                        measuresList.clear()
                    }
                }
            }
        }
    }
    fun saveMealsToDatabaseButtonClicked(view: View) {
        runBlocking {
            launch {
                withContext(Dispatchers.IO){
                    for(item in retrievedMealsList){
                        mealsDao.insertMeal(item)
                        val myString: String? = item.mealName+" ingredient - " + item.ingredient1 + "Drink Alternative" + item.drinkAlternate
                        Log.d("Details", myString.toString())
                    }
                    runOnUiThread {
                        saveMealsToDatabase.isEnabled = false
                    }
                }
            }
        }

    }
}                       