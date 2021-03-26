package com.mobi.mobidrivers.repository.rest

import android.util.Log
import com.example.mvvmstructurekotlin.constants.Constants
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * All the API services are here.
 */
interface ApiService {


    @GET("data.json")
    fun ProductList(): Observable<ProductListResponse>


    companion object {
        lateinit var retrofit: Retrofit

        fun create(): ApiService {

            val httpLogger = HttpLoggingInterceptor()
            httpLogger.setLevel(HttpLoggingInterceptor.Level.BODY)

            val timeout = TimeUnit.SECONDS.toSeconds(100)

            val client = OkHttpClient.Builder()
            client.readTimeout(timeout, TimeUnit.SECONDS)
            client.writeTimeout(timeout, TimeUnit.SECONDS)
            client.connectTimeout(timeout, TimeUnit.SECONDS)

            val headerInterceptor = object : Interceptor {

                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = request?.newBuilder()?.addHeader(
                        "X-Acc",
                        "xyz"
                    )?.build()

                    var response = chain.proceed(request)
                    if (response.code == 509) {
                        Log.e("headerInterceptor: ", "return 509")
                        return response
                    } else {
                        return response
                    }

                }

            }

            client.addInterceptor(headerInterceptor)
            client.addInterceptor(httpLogger)

            retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .baseUrl(Constants.baseURL)
                .build()

            return retrofit.create(ApiService::class.java)

        }


    }

}
