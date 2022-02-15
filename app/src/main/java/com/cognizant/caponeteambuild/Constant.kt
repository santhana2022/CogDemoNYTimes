package com.cognizant.caponeteambuild

class Constant {

    object HTTP {
        const val BASE_URL_V0 = "https://api.nytimes.com/svc/topstories/v2/"
        const val BASE_URL_V1 = "https://grjdhf09al.execute-api.us-east-1.amazonaws.com/dev/"
        const val API_KEY = "LcKeKsGydBd8zJODuMGUy0jyKs6MtkvS"
    }

    object Vals {
        const val TIME_MILLIS_24H: Long = 24 * 60 * 60 * 1000
    }

    object appText {
        const val NO_INTERNET: String = "Please, Connect your internet connection."
    }

    object NytAPI {
        val SECTIONS = listOf(
            "politics", "sports", "travel", "arts", "automobiles", "books", "business",
            "fashion", "food", "health", "home", "insider", "magazine", "movies", "nyregion",
            "obituaries", "opinion", "realestate", "science", "sundayreview", "technology",
            "theater", "t-magazine", "upshot", "us", "world"
        )
    }
}