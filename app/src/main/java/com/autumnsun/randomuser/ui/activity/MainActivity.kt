package com.autumnsun.randomuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
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
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.autumnsun.randomuser.model.Results

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private val apiClient by lazy { ApiClient.getApiClient() }
    private lateinit var adapter: UserAdapter

    companion object {
        const val EXTRA_RESULT_EXTRA = "extra_result_item"
        const val EXTRA_RESULT_TRANSITION_NAME = "extra_transition_name"
    }

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
                    adapter = UserAdapter(response.body()?.userList!!, this@MainActivity)
                    recyclerview.adapter = adapter
                    progressBar.gone()
                    recyclerview.visible()
                    swipe_refresh_layout.isRefreshing = false
                }
            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { Log.d("onQueryTextSubmit", it) }
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { Log.d("onQueryTextChange", it) }
                adapter.getFilter().filter(newText)
                return false
            }

        })
        return true
    }

    override fun onUserClickListener(results: Results, sharedImageView: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_RESULT_EXTRA, results)
        intent.putExtra(EXTRA_RESULT_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }

}