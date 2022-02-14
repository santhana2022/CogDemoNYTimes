package com.cognizant.caponeteambuild

class Constant {

    object HTTP {
        const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
        const val API_KEY  = "LcKeKsGydBd8zJODuMGUy0jyKs6MtkvS"
    }

    object Vals {
        const val TIME_MILLIS_24H: Long =  24 * 60 * 60 * 1000
    }

    object appText {
        const val NO_INTERNET: String =  "Please, Connect your internet connection."
    }
}