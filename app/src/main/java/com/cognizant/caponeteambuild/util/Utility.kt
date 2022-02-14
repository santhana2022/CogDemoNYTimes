package com.cognizant.caponeteambuild.util

import android.content.Context
import android.net.ConnectivityManager

class Utility {
    //Utility Class
    object NETWORK {
        fun checkInternet(context: Context): Boolean {
            val ConnectionManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = ConnectionManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}