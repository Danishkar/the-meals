package com.example.mobile_cw_2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.Serializable
import java.net.URL
class SearchForMeal : AppCompatActivity() {
    private lateinit var mealsDao: MealsDao
    lateinit var searchMealsTextBox:EditText
    lateinit var linearLayout1: LinearLayout
    var foundIngredientAndMeasures = java.lang.StringBuilder()
    lateinit var errorMessage2:TextView
    var meals =  mutableListOf<Meals>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meal)
        mealsDao = DatabaseSingleton.mealsDao
        searchMealsTextBox = findViewById(R.id.searchMealsTextBox)
        linearLayout1 = findViewById(R.id.linearLayout1)
        errorMessage2 = findViewById(R.id.errorMessage2)
//        retreving the variable state on orientation changes
        if (savedInstanceState != null) {
            val foundIngredientAndMeasure = savedInstanceState.getString("foundIngredientAndMeasures")
            foundIngredientAndMeasures = StringBuilder(foundIngredientAndMeasure)
            errorMessage2.text = savedInstanceState.getString("errorMessage2")
            meals = savedInstanceState.getSerializable("mealsList") as MutableList<Meals>
            addMealToLinearLayout(meals)
        }
    }

//        Storing the variable state on orientation changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("foundIngredientAndMeasures", foundIngredientAndMeasures.toString())
        outState.putString("errorMessage2",errorMessage2.text.toString())
        outState.putSerializable("mealsList", meals as Serializable)
    }
//  function to add all the meals that are retrieved from the database to the linear layout
    private fun addMealToLinearLayout(meals: List<Meals>) {
        if (meals.isNotEmpty()){
            for (meal in meals) {
                GlobalScope.launch(Dispatchers.IO) {
                    val layoutInflater = LayoutInflater.from(this@SearchForMeal)
                    val rootView = layoutInflater.inflate(R.layout.list_item, null)
                    val mealName = meal.mealName
                    val drinkAlternative = meal.drinkAlternate
                    val category = meal.category
                    val area = meal.area
                    val instruction = meal.instructions
                    val slicedInstruction: String
                    if (instruction != null) {
                        slicedInstruction = instruction.substring(0, 30) + "...."
                    } else {
                        slicedInstruction = "null"
                    }
                    val tags = meal.tags
                    val youtube = meal.youtube
                    val mealThumb = meal.mealThumb

                    val myImageView = rootView.findViewById<ImageView>(R.id.list_item_image)
                    val drawable: Drawable? = loadImageFromWebOperations(mealThumb)
                    // Update the UI on the main thread with the loaded image
                    myImageView.setImageDrawable(drawable)

                    val myMealNameView = rootView.findViewById<TextView>(R.id.list_item_meal_name)
                    myMealNameView.text = mealName

                    val myMealOtherData = rootView.findViewById<TextView>(R.id.list_item_meal_other_data)
                    myMealOtherData.setText(
                        "Drink Alternate : $drinkAlternative,\nCategory : $category" +
                                ",\nArea : $area,\nInstructions : $slicedInstruction,\nTags : $tags,\nYoutube : $youtube"
                    )

                    val myMealIngredientAndMeasures = rootView.findViewById<TextView>(R.id.list_item_meal_ingredient_measures)
                    foundIngredientAndMeasures.append("\nIngredient1 : ${meal.ingredient1}" + ", Measure1 : ${meal.measure1}")
                    foundIngredientAndMeasures.append("\nIngredient2 : ${meal.ingredient2}" + ", Measure2 : ${meal.measure2}")
                    foundIngredientAndMeasures.append("\nIngredient3 : ${meal.ingredient3}" + ", Measure3 : ${meal.measure3}")
                    foundIngredientAndMeasures.append("\nIngredient4 : ${meal.ingredient4}" + ", Measure4 : ${meal.measure4}")
                    foundIngredientAndMeasures.append("\nIngredient5 : ${meal.ingredient5}" + ", Measure5 : ${meal.measure5}")
                    foundIngredientAndMeasures.append("\nIngredient6 : ${meal.ingredient6}" + ", Measure6 : ${meal.measure6}")
                    foundIngredientAndMeasures.append("\nIngredient7 : ${meal.ingredient7}" + ", Measure7 : ${meal.measure7}")
                    foundIngredientAndMeasures.append("\nIngredient8 : ${meal.ingredient8}" + ", Measure8 : ${meal.measure8}")
                    foundIngredientAndMeasures.append("\nIngredient9 : ${meal.ingredient9}" + ", Measure9 : ${meal.measure9}")
                    foundIngredientAndMeasures.append("\nIngredient10 : ${meal.ingredient10}" + ", Measure10 : ${meal.measure10}")
                    foundIngredientAndMeasures.append("\nIngredient11 : ${meal.ingredient11}" + ", Measure11 : ${meal.measure11}")
                    foundIngredientAndMeasures.append("\nIngredient12 : ${meal.ingredient12}" + ", Measure12 : ${meal.measure12}")
                    foundIngredientAndMeasures.append("\nIngredient13 : ${meal.ingredient13}" + ", Measure13 : ${meal.measure13}")
                    foundIngredientAndMeasures.append("\nIngredient14 : ${meal.ingredient14}" + ", Measure14 : ${meal.measure14}")
                    foundIngredientAndMeasures.append("\nIngredient15 : ${meal.ingredient15}" + ", Measure15 : ${meal.measure15}")
                    foundIngredientAndMeasures.append("\nIngredient16 : ${meal.ingredient16}" + ", Measure16 : ${meal.measure16}")
                    foundIngredientAndMeasures.append("\nIngredient17 : ${meal.ingredient17}" + ", Measure17 : ${meal.measure17}")
                    foundIngredientAndMeasures.append("\nIngredient18 : ${meal.ingredient18}" + ", Measure18 : ${meal.measure18}")
                    foundIngredientAndMeasures.append("\nIngredient19 : ${meal.ingredient19}" + ", Measure19 : ${meal.measure19}")
                    foundIngredientAndMeasures.append("\nIngredient20 : ${meal.ingredient20}" + ", Measure20 : ${meal.measure20}")
                    runOnUiThread {
                        myMealIngredientAndMeasures.setText(foundIngredientAndMeasures)
                        linearLayout1.addView(rootView)
                        foundIngredientAndMeasures.setLength(0)
                    }
                    Log.d("Printing", meal.mealName)
                }
            }
        }else{
            errorMessage2.text = "No meals found in the given search query."
        }
    }

//    function to check if a string contains any integer.
    fun containsInt(input: String): Boolean {
        val chars = input.toCharArray()
        for (c in chars) {
            if (c.isDigit()) {
                return true
            }
        }
        return false
    }

//    function when the search for meals button is clicked.
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    fun searchButtonClicked(view: View) {

        linearLayout1.removeAllViews()
        errorMessage2.text = ""
        foundIngredientAndMeasures.setLength(0)
        val searchQuery = searchMealsTextBox.text.toString()
        val userInputContainInt = containsInt(searchQuery)
        if(searchQuery == ""){
            errorMessage2.text = "Input cannot be empty"
        }
        else if (userInputContainInt){
            errorMessage2.text = "Input cannot contain Integer."
        }else{
            runBlocking {
                launch {
                    // run the code of the coroutine in a new thread
                    withContext(Dispatchers.IO) {
                        meals = mealsDao.searchMeals(searchQuery).toMutableList()
                        addMealToLinearLayout(meals)
                    }
                }
            }
        }
    }

//    function to load the image from the given url as the parameter
    private fun loadImageFromWebOperations(url: String?): Drawable? {
        try {
            val inputStream: InputStream = URL(url).content as InputStream
            return Drawable.createFromStream(inputStream, "src name")
        } catch (e: Exception) {
            println("Exc=$e")
            return null
        }
    }

//    function the back button is clicked
    fun backButtonClicked(view: View) {
        val mainIntent = Intent(this,MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}