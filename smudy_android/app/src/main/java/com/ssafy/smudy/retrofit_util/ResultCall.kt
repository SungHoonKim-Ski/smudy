package com.ssafy.smudy.retrofit_util

import android.util.Log
import com.ssafy.util.NetworkException
import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class ResultCall<T>(
    private val callDelegate: Call<T>
) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> {
        return ResultCall(callDelegate.clone())
    }

    override fun execute(): Response<Result<T>> {
        val response = callDelegate.execute()
        return if (response.isSuccessful && response.body() != null ) {
            Response.success(Result.success(response.body()!!))
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override fun isExecuted(): Boolean {
        return callDelegate.isExecuted
    }

    override fun cancel() {
        callDelegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return callDelegate.isCanceled
    }

    override fun request(): Request {
        return callDelegate.request()
    }

    override fun timeout(): Timeout {
        return callDelegate.timeout()
    }

    override fun enqueue(callback: Callback<Result<T>>) =
        callDelegate.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                val body = response.body()
                if (response.isSuccessful) {
                    if (body != null) {
                        // body and data is not null
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(NullPointerException("response body is null")))
                        )
                    }
                } else {
                    val errorBodyString = response.errorBody()?.string()
                    Log.e("TAG", "onResponse: $errorBodyString")
                    val (message,code) = try {
                        if (!errorBodyString.isNullOrBlank()) {
                            listOf(
                                JSONObject(errorBodyString).getString("message"),
                                JSONObject(errorBodyString).getString("code")
                            )
                        } else {
                            listOf("","")
                        }
                    } catch (e: Exception) {
                        listOf("","")
                    }
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(NetworkException(code,message)))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                println("fail : ${t}")
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(
                        when(t){
                            is IOException -> IOException("네트워크 에러",t)
                            else -> t
                        }
                    ))
                )
            }
        })

}