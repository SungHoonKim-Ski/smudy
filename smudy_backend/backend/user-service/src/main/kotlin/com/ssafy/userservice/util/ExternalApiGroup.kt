package com.ssafy.userservice.util

import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import org.bson.json.JsonObject
import org.springframework.boot.json.JsonParser
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


enum class ExternalApiGroup (
        private val url: String
        , private val authKey: String
        , private val method: HttpMethod
){
    NAVER_API("https://www.naver.com/blabla/api", "인증키", HttpMethod.GET)
    , KAKAO_API("https://www.kakao.com/blabla/api", "인증키", HttpMethod.GET)
    , GOOGLE_API("https://www.google.com/blabla/api", "인증키", HttpMethod.POST);

//    fun getSettlementData(params: Map<String, String>?): JsonObject {
//        val webClient: WebClient = WebClient.create()
//        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
//        formData.setAll(params!!)
//        var response = ""
//        when (method) {
//            HttpMethod.GET -> response = webClient.get()
//                    .uri(url, (uriBuilder -> uriBuilder.queryParams(formData).build()))
//                    .header(HttpHeaders.ACCEPT, "*/*")
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer $authKey")
//                    .retrieve()
//                    .bodyToMono(String::class.java)
//                    .block()
//
//            HttpMethod.POST -> response = webClient.post()
//                    .uri(url)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .body(BodyInserters.fromFormData(formData))
//                    .retrieve()
//                    .bodyToMono(String::class.java)
//                    .block()
//        }
//        assert(response != null)
//        return JsonParser.parseString(response).getAsJsonObject()
//    }
}