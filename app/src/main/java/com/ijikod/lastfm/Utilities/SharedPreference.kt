package com.ijikod.lastfm.Utilities

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private lateinit var prefs: SharedPreferences

    private const val LAST_SEARCH_QUERY: String = "last_search_query"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(LAST_SEARCH_QUERY, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }
}