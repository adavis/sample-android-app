package info.adavis.sampleapp.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Injector {

    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    fun getApi(): SampleAPI {
        return provideRetrofit().create(SampleAPI::class.java)
    }

}
