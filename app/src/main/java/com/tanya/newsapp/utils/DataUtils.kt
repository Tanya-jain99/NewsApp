package com.tanya.newsapp.utils

import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.data.model.Country

class DataUtils {

    companion object {
        fun getCountries(): List<Country> {
            return listOf( Country("USA", "us"),
                Country("UAE", "ae"),
                Country("Argentina", "ar"),
                Country("Austria", "at"),
                Country("Australia", "au"),
                Country("Belgium", "be"),
                Country("Bulgaria", "bg"),
                Country("Brazil", "br"),
                Country("Canada", "ca"),
                Country("Switzerland", "ch"),
                Country("China", "cn"),
                Country("Columbia", "co")
            )
        }

        fun getLanguages(): List<Language> {
            return listOf( Language("English", "en"),
                Language("German", "de"),
                Language("Arabic", "ar"),
                Language("Spanish", "es"),
                Language("French", "fr"),
                Language("Hebrew", "he"),
                Language("Portuguese", "pt"),
                Language("Dutch", "nl"),
                Language("Norwegian", "no")
            )
        }
    }


}