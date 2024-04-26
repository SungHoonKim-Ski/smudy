package com.ssafy.backend_dummy.auth_service.util

import com.ssafy.backend_dummy.user_service.util.CommonResponseCode
import org.springframework.stereotype.Service

@Service
class AuthResponseService {
    fun getSuccessMessageResult(message: String): CommonResult {
        return CommonResult(CommonResponseCode.SUCCESS.code, message)
    }

    // 단일건 결과를 처리하는 메소드
    fun <T> getSuccessSingleResult(data: T, message: String): SingleResult<T> {
        return SingleResult(data, CommonResponseCode.SUCCESS.code, message)
    }

    fun <T> getSingleResult(data: T, message: String, code: Int): SingleResult<T> {
        return SingleResult(data, code, message)
    }

    // 다중건 결과를 처리하는 메소드
    fun <T> getSuccessListResult(list: List<T>): ListResult<T> {
        return ListResult(list, CommonResponseCode.SUCCESS.code, "SUCCESS")
    }

    // 다중건 결과를 처리하는 메소드 + msg 세팅
    fun <T> getSuccessListResult(list: List<T>, message: String): ListResult<T> {
        return ListResult(list, CommonResponseCode.SUCCESS.code, message)
    }

    // 성공 결과만 처리하는 메소드
    fun getSuccessResult(): CommonResult {
        return CommonResult(CommonResponseCode.SUCCESS.code, "SUCCESS");
    }

    // 성공 결과만 처리하는 메소드
    fun getSuccessResult(message: String): CommonResult {
        return CommonResult(CommonResponseCode.SUCCESS.code, message)
    }

    private fun setSuccessResult(result: CommonResult) {
        result.code = CommonResponseCode.SUCCESS.code
        result.message = result.message
    }
}