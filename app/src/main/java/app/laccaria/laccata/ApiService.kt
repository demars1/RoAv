package app.laccaria.laccata

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("app")
    suspend fun sendData(@Body data: DataInfo): Response<DataInfo>

    @GET("app/keitaro/campaignByName?")
    suspend fun getPost(@Query("geo") geotag: String): Response<GetData>

}

interface ApiServKlo {
    @GET("L1YBLwDX")
    suspend fun getResource(): Response<ResponseBody>
}