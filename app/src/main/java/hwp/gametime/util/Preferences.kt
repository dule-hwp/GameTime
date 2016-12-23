package hwp.gametime.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by dusan_cvetkovic on 12/7/16.
 */


class Preferences{

}

fun Activity.saveToPrefs(key: String, string: Set<String>){
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
    val editor :SharedPreferences.Editor = sharedPref.edit()
    editor.putStringSet(key, string)
    editor.apply()
}

fun Activity.getSetFromPrefs(key: String) : MutableSet<String>{
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
    val prefsSet = sharedPref.getStringSet(key, mutableSetOf<String>())
//    val editor :SharedPreferences.Editor = sharedPref.edit()
//    editor.putStringSet(key, string)
//    editor.apply()
    return prefsSet
}

