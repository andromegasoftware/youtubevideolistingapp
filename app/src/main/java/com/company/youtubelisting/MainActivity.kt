package com.company.youtubelisting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var categoryList = ArrayList<Category>()
    var adp = adapter_class(categoryList,{categoryItem:Category->categoryClickListener(categoryItem)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adp
        loadCategories()
    }

    fun loadCategories(){
        lineerlayoutLoadingCategories.visibility = View.VISIBLE     //progressbar will appear when loading the categories
        val query = ParseQuery<ParseObject>("category")
        query.orderByAscending("name")
        //query.whereEqualTo("name", "Java")    //this is an example for empty categories
        query.findInBackground { list,e ->
            lineerlayoutLoadingCategories.visibility = View.GONE     //progressbar will be gone when loading the categories
            if (e==null){
                //no error
                Log.d("browse","there is no error")
                if (list.size>0){
                    //there is categories retrieved
                    Log.d("browse","there is categories retrieved " + list.size)
                    for (category in list)
                    {
                        categoryList.add(Category(category.objectId, category.get("name").toString(), category.getParseFile("picture")))
                    }
                    adp.notifyDataSetChanged()
                    var namesofLoadedCategories = ""
                    for (c in categoryList){
                       namesofLoadedCategories += c.name+"\n"
                    }
                    //category_act_txt.text = namesofLoadedCategories
                }
                else{
                    //there is no categories in app backend
                    category_error.visibility = View.VISIBLE
                    textViewError.text = "There is no category in this name"

                }
            }
            else{
                //there is error
                category_error.visibility = View.VISIBLE
                textViewError.text = "There is an error. please check your internet connection"
                //buttonRetry.visibility = View.VISIBLE
                buttonRetry.setOnClickListener {
                    loadCategories()
                    category_error.visibility = View.GONE
                }
            }

        }
    }

    fun categoryClickListener(category: Category) {

        val intent = Intent(this, BrowseVideosActivity::class.java)
        intent.putExtra("selectedCategoryName", category.name)
        intent.putExtra("selectedCategoryObjectId", category.objectId)
        startActivity(intent)
    }
}