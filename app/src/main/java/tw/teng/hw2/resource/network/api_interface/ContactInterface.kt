package tw.teng.hw2.resource.network.api_interface

import retrofit2.Call
import retrofit2.http.GET
import tw.teng.hw2.resource.network.model.APIResponse

interface ContactInterface {
    @GET("/status")
    fun getStatus(): Call<APIResponse>
}