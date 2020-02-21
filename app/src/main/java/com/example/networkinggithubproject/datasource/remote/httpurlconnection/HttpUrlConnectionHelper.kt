package com.example.networkinggithubproject.datasource.remote.httpurlconnection

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnectionHelper
{
    fun getResponse(url: String) : String
    {
        var returnString = ""
        val gitUrl = URL(url)
        val connection = gitUrl.openConnection() as HttpURLConnection

        val stream = connection.inputStream

        var read = stream.read()
        while(read > 0)
        {
            returnString = "$returnString${read.toChar()}"
            read = stream.read()
        }

        stream.close()

        return returnString
    }
}