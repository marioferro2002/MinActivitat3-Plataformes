package com.example.netdownload.viewmodel

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.netdownload.util.WebPageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * ViewModel responsible for handling location updates in the application.
 * Utilizes Android's FusedLocationProviderClient for efficient location retrieval.
 */

class NetViewModel(application: Application) : AndroidViewModel(application) {

    // Base URL for requests
    private val BASE_URL = "https://cv.udl.cat/portal/"

    // Retrofit instance for making HTTP requests
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    // Service for web page requests
    private val webPageService = retrofit.create(WebPageService::class.java)

    // LiveData to handle the result of web page download
    val webPageContent = MutableLiveData<String?>()

    // Function to download the web page
    fun downloadWebPage() {
        viewModelScope.launch(Dispatchers.IO) {
            webPageService.getPageContent(BASE_URL).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val content = response.body()
                        webPageContent.setValue(content)
                    } else {
                        webPageContent.postValue("El servidor no ha acceptat")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    webPageContent.postValue("Error al agafar url")
                }
            })
        }
    }

    // LiveData to handle the URL of the image
    val imageUrl = MutableLiveData<String?>()

    // Function to set the URL of the image
    fun setImageUrl(url: String) {
        imageUrl.value = url
    }

    // Function to download the image and load it into the view
    fun downloadAndSetImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context.applicationContext)
            .load(url)
            .into(imageView)
    }
}
