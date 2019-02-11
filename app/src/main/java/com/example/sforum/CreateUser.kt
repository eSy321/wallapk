package com.example.sforum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent



class CreateUser : AppCompatActivity() {
    val client =  OkHttpClient()
    val FORM = MediaType.parse("application/x-www-form-urlencoded")

    fun httpPost(url: String, body: RequestBody, success: (response: Response) -> Unit, failure: () -> Unit){
        val request = Request.Builder()
            .url(url)
            .post(body)
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
    fun CreateNewStudent(index: String,name: String,password: String, password_repeat: String){
        Toast.makeText(this, "Tworzenie... (" + index + ":" + password + ")", Toast.LENGTH_SHORT).show()
        val url = "http://wallapk.herokuapp.com/students"
        val body = RequestBody.create(FORM, "student[index]=" + index + "&student[name]=" + name + "&student[password]=" + password + "&student[password_confirmation]=" + password_repeat )


        httpPost(url, body,
            fun (response: Response){
                Log.v("INFO","Succeeded")
                val response_string = response.body().string()
                //val json = JSONObject(response_string).getString()
                Log.v("RESPONDREG", response_string)
                this.runOnUiThread(){
                    val intent = android.content.Intent(this, HomeActivity::class.java)
                    startActivity(intent)}
            },
            fun (){
                Log.v("INFO","Failed")
            })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        if(name_field.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        create_student_button.setOnClickListener{


            val name = name_field.text.toString()
            val index = index_field.text.toString()
            val password = haslo_field.text.toString()
            val password_repeat = potwierdzenie_field.text.toString()

            if (name_field.toString().isEmpty() || name_field.text.toString().length < 3){ name_field?.error = "Imie nie może byc krótsze niz 3 znaki"}
            else if (index_field.text.toString().isEmpty() || index_field.text.toString().length < 6) {index_field?.error = "Index musi mieć dokładnie 6 znakow"}
            else if (haslo_field.text.toString().isEmpty() || haslo_field.text.toString().length<6) {haslo_field?.error = "Hasło musi mieć ponad 3 znaki"}
            else if (haslo_field.text.toString() != potwierdzenie_field.text.toString()) { potwierdzenie_field?.error = "Hasła się nie zgadzają"}
            else {
                CreateNewStudent(index, name, password, password_repeat)
            }

        }
    }
}

