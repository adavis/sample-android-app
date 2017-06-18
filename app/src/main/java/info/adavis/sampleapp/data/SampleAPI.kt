package info.adavis.sampleapp.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SampleAPI {

    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/posts")
    fun getUsersPosts(@Query("userId") userId: Long): Single<List<Post>>

}