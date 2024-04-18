package com.example.netdownload.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    // URL base para las solicitudes
    private val BASE_URL = "https://circuitotormenta.com/"

    // Retrofit instancia para realizar solicitudes HTTP
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    // Servicio para las solicitudes de página web
    private val webPageService = retrofit.create(WebPageService::class.java)

    // LiveData para manejar el resultado de la descarga de la página web
    val webPageContent = MutableLiveData<String?>()

    // Función para descargar la página web
    fun downloadWebPage() {
        viewModelScope.launch(Dispatchers.IO) {
            webPageService.getPageContent(BASE_URL).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val content = response.body()
                        webPageContent.postValue(content)
                    } else {
                        webPageContent.postValue("error al agafar url")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    webPageContent.postValue("error al agafar url")
                }
            })
        }
    }
}