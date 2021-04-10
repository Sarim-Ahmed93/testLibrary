package com.autoapkupdate

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Handler
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class CheckForLatestVersion(context: Context,fileURL: String) {
    private lateinit var fileUrl : String
    private lateinit var context : Context
    private var versionCode : Int? = null
    private var versionName : String? = null

    init {
        this.fileUrl = fileURL
        this.context = context

    }
   private fun getVersion() : Int?
    {
        var newVersionAvailable : Boolean =  false
        val handler = Handler()
        val timer = Timer()

                    try {
                        object : Thread() {
                            override fun run() {
                                super.run()
                                val stringBuilder = StringBuilder()
                                try {
                                    val url = URL(fileUrl)
                                    val stream = BufferedReader(InputStreamReader(url.openStream()))
                                    var string: String?
                                    while (stream.readLine().also { string = it } != null) {
                                        stringBuilder.append(string)
                                    }
                                    stream.close()
                                    try {
                                        val json = stringBuilder.toString()
                                        val obj = JSONObject(json)
                                        if (obj != null) {versionCode = obj.getInt("versionCode")}
                                        /*val manager: PackageManager = context.getPackageManager()
                                        val info: PackageInfo = manager.getPackageInfo(context.getPackageName(), 0)
                                        val currentVersion = info.longVersionCode*/
                                        /*if(versionCode!! >currentVersion)
                                        {
                                            newVersionAvailable = true
                                        }
                                        else
                                        {
                                            newVersionAvailable = false
                                        }*/
                                    } catch (e: JSONException) {
                                    } finally {
                                    }

                                } catch (e: MalformedURLException) {

                                } catch (e: IOException) {

                                }

                            }
                        }.start()
                    } catch (e: java.lang.Exception) { // error, do something
                    }
        return versionCode

    }


}