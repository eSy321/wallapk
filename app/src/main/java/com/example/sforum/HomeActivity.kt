package com.example.sforum

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class   HomeActivity : AppCompatActivity() {

    val STUDENT_INDEX = "com.example.sforum.STUDENT_INDEX"
    val STUDENT_NAME = "com.example.sforum.STUDENT_NAME"

    val client =  OkHttpClient()

    fun httpGet(url: String, success: (response: Response) -> Unit, failure: () -> Unit){
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                failure()
            }

            override fun onResponse(call: Call, response: Response) {
                success(response)
            }
        })
    }



    fun getStudents() {
        Toast.makeText(this, "Pobieranie...", Toast.LENGTH_SHORT).show()
        val url = "http://wallapk.herokuapp.com/students"

        httpGet(url,
            fun (response: Response){
                val response_string = response.body()?.string()
                val json = JSONArray(response_string)

                var partList = ArrayList<PartData>()
                for (i in 0..(json.length() - 1)) {

                    val item = json.getJSONObject(i)
                    partList.add(PartData(item.getLong("id"), item.getLong("index"), item.getString("name")))
                }

                this.runOnUiThread {
                    get_students.adapter = PartAdapter(partList, { partItem : PartData -> partItemClicked(partItem) })
                }
            },
            fun (){
                Log.v("INFO","Failed")
            })

    }

    fun getCourses() {
        Toast.makeText(this, "Pobieranie...", Toast.LENGTH_SHORT).show()
        val url = "http://wallapk.herokuapp.com/courses"

        httpGet(url,
            fun (response: Response){
                val response_string = response.body()?.string()
                val json = JSONArray(response_string)

                var partList = ArrayList<PartData2>()
                for (i in 0..(json.length() - 1)) {

                    val item = json.getJSONObject(i)

                    partList.add(PartData2(item.getLong("id"), item.getLong("code"), item.getString("name")))
                }

                this.runOnUiThread {
                    get_students.adapter = PartAdapter2(partList, { partItem2 : PartData2 -> partItemClicked2(partItem2) })
                }
            },
            fun (){
                Log.v("INFO","Failed")
            })

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        get_students.layoutManager = LinearLayoutManager(this)

        get_students.hasFixedSize()

        var calcTest = ClassWithConstructorProperties(10, 20)
        Log.d("Tests", "Calculation result: " + calcTest.calculate())

        testFunctionParameters( {a : Int, b : Int -> a + b } )

        logout_button.setOnClickListener {
            Toast.makeText(this, "Wylogowuje", Toast.LENGTH_SHORT).show()
            val intent = android.content.Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        get_students_button.setOnClickListener {
            getStudents()
        }
        get_courses_button.setOnClickListener {
            getCourses()
        }
        create_user_page_button.setOnClickListener{
            val intent = android.content.Intent(this, CreateUser::class.java)
            startActivity(intent)
        }
    }

    private fun partItemClicked(partItem : PartData) {
        Toast.makeText(this, "Clicked: ${partItem.name}", Toast.LENGTH_LONG).show()

        val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
        val extras = Bundle()

        extras.putString("id", partItem.id.toString())
        extras.putString("index", partItem.index.toString())
        extras.putString("name", partItem.name)
        showDetailActivityIntent.putExtras(extras)
        startActivity(showDetailActivityIntent)
    }
    private fun partItemClicked2(partItem2 : PartData2) {
        Toast.makeText(this, "Clicked: ${partItem2.name}", Toast.LENGTH_LONG).show()

        val showDetailActivityIntent = Intent(this, PartDetailActivity2::class.java)
        val extras = Bundle()
        extras.putString("id", partItem2.id.toString())
        extras.putString("code", partItem2.code.toString())
        extras.putString("name", partItem2.name)
        showDetailActivityIntent.putExtras(extras)
        startActivity(showDetailActivityIntent)
    }

    class ClassWithConstructorProperties constructor (var a: Int, var b: Int) {
        fun calculate() : Int {
            return a + b;
        }
    }

    private fun testFunctionParameters(performCalculation: (Int, Int) -> Int) {
        Log.d("Tests", "Calculation result: " + performCalculation(1, 2))
    }

}