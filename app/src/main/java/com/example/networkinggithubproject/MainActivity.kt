package com.example.networkinggithubproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkinggithubproject.datasource.remote.httpurlconnection.HttpUrlConnectionHelper
import com.example.networkinggithubproject.modelRepository.Owner
import com.example.networkinggithubproject.modelRepository.Repository
import com.example.networkinggithubproject.modelUser.User
import com.example.networkinggithubproject.view.adapter.ProfileAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gitHubUserURL = "https://api.github.com/users/AMillsap/repos"
        val gitHubProfileURL = "https://api.github.com/search/users?q=AMillsap"
        val httpUrlConnectionHelper = HttpUrlConnectionHelper()

        var jsonString = ""
        var jsonProfileString = ""

        Thread(Runnable
        {
            jsonString = httpUrlConnectionHelper.getResponse(gitHubUserURL)
            jsonProfileString = httpUrlConnectionHelper.getResponse(gitHubProfileURL)

            if(jsonProfileString.isNotBlank())
            {
                runOnUiThread( {
                val userResult = Gson().fromJson<User>(jsonProfileString, User::class.java)
                tvUserName.text = "GitHub Profile Name: " + userResult.items[0].login
                tvUserId.text = "GitHub ID: " + userResult.items[0].id.toString()
                tvUserUrl.text = "GitHub Repo Url: " + userResult.items[0].repos_url
                tvUserType.text = "GitHub Type: " + userResult.items[0].type
                })
            }
            if(jsonString.isNotBlank())
            {
                val userArray: ArrayList<Owner> = Gson().fromJson(
                    jsonString,
                    object : TypeToken<List<Owner?>?>() {}.type
                )
                val yourArray: ArrayList<Repository> = Gson().fromJson(
                    jsonString,
                    object : TypeToken<List<Repository?>?>() {}.type
                )
                runOnUiThread( {
                    rvUserRepositories.layoutManager = LinearLayoutManager(this)
                    rvUserRepositories.adapter = ProfileAdapter(yourArray)
                })
            }

        }).start()
    }
}
