package com.example.networkinggithubproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkinggithubproject.datasource.remote.httpurlconnection.HttpUrlConnectionHelper
import com.example.networkinggithubproject.modelRepository.Owner
import com.example.networkinggithubproject.modelRepository.Repository
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
        val httpUrlConnectionHelper = HttpUrlConnectionHelper()

        var jsonString = ""

        Thread(Runnable
        {
            jsonString = httpUrlConnectionHelper.getResponse(gitHubUserURL)

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
                    tvUserName.text = "GitHub Profile Name: " + userArray[0].login
                    tvUserId.text = "GitHub ID: " + userArray[0].id.toString()
                    tvUserUrl.text = "GitHub Repo Url: " + userArray[0].repos_url
                    tvUserType.text = "GitHub Type: " + userArray[0].type
                    rvUserRepositories.layoutManager = LinearLayoutManager(this)
                    rvUserRepositories.adapter = ProfileAdapter(yourArray)
                })
            }

        }).start()
    }
}
