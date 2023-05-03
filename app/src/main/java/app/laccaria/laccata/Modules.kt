package app.laccaria.laccata

import android.util.Log
import app.laccaria.laccata.main.VMforMain
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
        viewModel {
            VMforMain(get())
        }
    }

    val networkModule = module {
        single (named("OkHttpGet")) {

            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicm9sZXMiOlt7ImlkIjoyfV0sImlhdCI6MTY3MjE4NDQ0NH0.ONCAZr_r8Cdu1cePZz4FRP75ytLrDGtul2qzgkoqnCc")
                        .build()
                    val url = request.url()
                    Log.d("RequestUrlGotten", "Request URL: $url")
                    chain.proceed(request)
                }
                .build()
        }



        single (named("GetNet")){

            Retrofit.Builder()
                .baseUrl("http://185.46.9.229:4002/v1/")
                .client(get(named("OkHttpGet")))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single (named("GetNetRetro")){
            get<Retrofit>(named("GetNet")).create(ApiService::class.java)
        }
    }

val networkforKloModule = module {


    single(named("KloNet")) {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url()
                Log.d("TAG", "Request URL: $url")

                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://realavi.space/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single <ApiServKlo> (named("ApiServKl")){
        get<Retrofit>(named("KloNet")).create(ApiServKlo::class.java)
    }
}

