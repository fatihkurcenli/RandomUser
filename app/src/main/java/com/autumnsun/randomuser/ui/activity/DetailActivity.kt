package com.autumnsun.randomuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.autumnsun.randomuser.R
import com.autumnsun.randomuser.model.Results
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportStartPostponedEnterTransition()
        val results: Results =
            intent.extras?.getSerializable(MainActivity.EXTRA_RESULT_EXTRA) as Results
        detail_username_tv.text = "${results.name.first} ${results.name.last}"
        detail_email_tv.text = results.email
        detail_user_gender_tv.text = results.gender
        val imageTransitionName =
            intent.extras?.getString(MainActivity.EXTRA_RESULT_TRANSITION_NAME)
        detail_image.transitionName = imageTransitionName

        Picasso.get().load(results.picture.large).into(detail_image,object :Callback{
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                supportStartPostponedEnterTransition()
            }

        })

    }
}