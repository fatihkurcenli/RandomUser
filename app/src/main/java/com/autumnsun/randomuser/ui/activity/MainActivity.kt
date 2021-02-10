package com.autumnsun.randomuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.autumnsun.randomuser.R
import com.autumnsun.randomuser.data.ApiClient
import com.autumnsun.randomuser.model.UserResponse
import com.autumnsun.randomuser.ui.adapter.UserAdapter
import com.autumnsun.randomuser.util.gone
import com.autumnsun.randomuser.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val apiClient by lazy { ApiClient.getApiClient() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        progressBar.visible()
        recyclerview.gone()
        getUsers()
        swipe_refresh_layout.setOnRefreshListener { getUsers() }
    }

    private fun getUsers() {
        apiClient.getUsers(10).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("mainactivity", t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("mainActivity", response.body()?.userList?.size.toString())
                if (response.isSuccessful) {
                    recyclerview.adapter = UserAdapter(response.body()?.userList!!)
                    progressBar.gone()
                    recyclerview.visible()
                    swipe_refresh_layout.isRefreshing = false
                }
            }

        })
    }
}