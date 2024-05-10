package com.ssafy.userservice.config

import com.netflix.discovery.converters.Auto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import se.michaelthelin.spotify.SpotifyApi
import java.net.URI
import java.util.*

@Configuration
class SpotifyConfig(
    private var restTemplate: RestTemplate
) {

    @Value("\${spotify.client.id}")
    private lateinit var clientId: String

    @Value("\${spotify.client.secret}")
    private lateinit var clientSecret: String

    @Value("\${spotify.client.redirect-uri}")
    private lateinit var redirectUri: String

    @Bean
    fun spotifyApi(): SpotifyApi {
        val spotifyApi = SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(URI.create(redirectUri))
            .build()
        obtainAccessToken(spotifyApi) // 토큰을 설정
        return spotifyApi
    }

    private fun obtainAccessToken(spotifyApi: SpotifyApi) {
        val credentials = "$clientId:$clientSecret"
        val encodedCredentials = Base64.getEncoder().encodeToString(credentials.toByteArray())

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Authorization", "Basic $encodedCredentials")

        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("grant_type", "client_credentials")

        val entity = HttpEntity(formData, headers)

        val response = restTemplate.postForObject<Map<String, Any>>(
            "https://accounts.spotify.com/api/token", entity, Map::class.java
        )
        response["access_token"]?.toString()?.let {
            spotifyApi.accessToken = it
            println("Access token obtained and set successfully.")
        } ?: println("Error obtaining access token.")
    }
}

