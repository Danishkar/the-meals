package com.example.mobile_cw_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

class SearchForMealFromWeb : AppCompatActivity() {
    lateinit var searchMealsFromWebTextBox :EditText
    lateinit var mealsFromWebTextBox : TextView
    var nameFoundMeals = java.lang.StringBuilder()
    lateinit var errorMessage3:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meal_from_web)
        searchMealsFromWebTextBox = findViewById(R.id.searchMealsFromWebTextBox)
        mealsFromWebTextBox = findViewById(R.id.mealsFromWebTextBox)
        errorMessage3 = findViewById(R.id.errorMessage3)
        if (savedInstanceState != null) {
            val savedMeals = savedInstanceState.getString("nameFoundMeals")
            nameFoundMeals = StringBuilder(savedMeals)
            mealsFromWebTextBox.text = savedInstanceState.getString("mealsFromWebTextBox")
            errorMessage3.text = savedInstanceState.getString("errorMessage3")
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nameFoundMeals", nameFoundMeals.toString())
        outState.putString("mealsFromWebTextBox",mealsFromWebTextBox.text.toString())
        outState.putString("errorMessage3",errorMessage3.text.toString())
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
    fun searchFromWebButtonClicked(view: View) {
        errorMessage3.text = ""
        nameFoundMeals.setLength(0)
        mealsFromWebTextBox.text = ""
        var stb = StringBuilder()
        val userInputContainInt = containsInt(searchMealsFromWebTextBox.text.toString())
        if (userInputContainInt){
            errorMessage3.text = "Input cannot contain Integer."
        }else{
            val url_string = "https://www.themealdb.com/api/json/v1/1/search.php?s="+searchMealsFromWebTextBox.text.toString()
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
                        try {
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
                                val slicedInstruction : String
                                if(instruction != null){
                                    slicedInstruction = instruction.substring(0,30)+"...."
                                }else{
                                    slicedInstruction = "null"
                                }
                                nameFoundMeals.append("\"Meal\":\"$mealName\",\n\"DrinkAlternate\":\"$drinkAlternative\",\n\"Category\":\"$category\"" +
                                        ",\n\"Area\":\"$area\",\n\"Instructions\":\"$slicedInstruction\",\n\"Tags\":\"$tags\",\n\"Youtube\":\"$youtube \"")
                                for(y in 0 .. 19){
                                    if (meal["strIngredient"+(y+1)] != ""){
                                        val num = y+1
                                        val ingredient = meal["strIngredient"+(y+1)]
                                        nameFoundMeals.append(",\n\"Ingredient$num\":\"$ingredient\"")
                                    }
                                }
                                for(y in 0 .. 19){
                                    if (meal["strMeasure"+(y+1)] != ""){
                                        val num = y+1
                                        val measure =  meal["strMeasure"+(y+1)]
                                        nameFoundMeals.append(",\n\"Measure$num\":\"$measure\"")
                                    }
                                }
                                nameFoundMeals.append("\n\n")
                            }
                            runOnUiThread {
                                mealsFromWebTextBox.setText(nameFoundMeals)
                            }
                        } catch (e: JSONException) {
                            nameFoundMeals.append("Not Found.Try Again")
                            mealsFromWebTextBox.setText(nameFoundMeals)
                        }
                    }
                }
            }
        }

    }

}