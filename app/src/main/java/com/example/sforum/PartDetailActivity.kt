package com.example.sforum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_part_detail.*

class PartDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_detail)

        var intentThatStartedThisActivity = getIntent()

        if (intentThatStartedThisActivity.hasExtra("index")) {
            var partId = intentThatStartedThisActivity.getStringExtra("index")
            tv_item_id.text = partId
        }
        if (intentThatStartedThisActivity.hasExtra("name")) {
            var partId = intentThatStartedThisActivity.getStringExtra("name")
            tv_item_name.text = partId
        }
    }
}
