package a77777_888.me.t.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class HhRetrofitSource {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(createLoggingInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(HeadHunterAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}