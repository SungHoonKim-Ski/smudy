package com.ssafy.userservice.controller

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.postgre.entity.LearnReportPronounce
import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import com.ssafy.userservice.db.postgre.repository.LearnReportPronounceRepository
import com.ssafy.userservice.dto.request.SignUpRequest
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.dto.response.feign.FillResponse
import com.ssafy.userservice.dto.response.feign.PronounceResponse
import com.ssafy.userservice.service.SongService
import com.ssafy.userservice.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user/feign")
class FeignController (
        private val songService: SongService,
        private val userService: UserService,
        private val objectMapperConfig: ObjectMapperConfig,
        private val pronounceRepository: LearnReportPronounceRepository
){
    private val logger = KotlinLogging.logger{ }

    @GetMapping("/fill/{songId}")
    fun getFillQuiz(@PathVariable("songId") songId: String) : FillResponse {
        return songService.getFillQuiz(songId)
    }

    @GetMapping("/pick/{songId}")
    fun getPickQuiz(@PathVariable("songId") songId: String) : SongSimple {
        return songService.getPickQuiz(songId)
    }

    @GetMapping("/express/{songId}")
    fun getExpressQuiz(@PathVariable("songId") songId: String) : SongSimple {
        return songService.getExpressQuiz(songId)
    }

    @GetMapping("/pronounce/{songId}")
    fun getPronounceQuiz(@PathVariable("songId") songId: String) : PronounceResponse {
        return songService.getPronounceQuiz(songId)
    }

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest) : String {
        return userService.signup(request)
    }

    @GetMapping("/test")
    fun test() {
        val mapper = objectMapperConfig.getObjectMapper()

        val json = "        \"lyricAiAnalyze\": {\n" +
                "            \"ref_timestamps\": [\n" +
                "                {\n" +
                "                    \"word\": \"I\",\n" +
                "                    \"start_time\": 0.0,\n" +
                "                    \"end_time\": 0.4\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"used\",\n" +
                "                    \"start_time\": 0.4,\n" +
                "                    \"end_time\": 0.6\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"to\",\n" +
                "                    \"start_time\": 0.6,\n" +
                "                    \"end_time\": 0.6\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"rule\",\n" +
                "                    \"start_time\": 0.6,\n" +
                "                    \"end_time\": 0.9\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"the\",\n" +
                "                    \"start_time\": 0.9,\n" +
                "                    \"end_time\": 1.0\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"world\",\n" +
                "                    \"start_time\": 1.0,\n" +
                "                    \"end_time\": 1.1\n" +
                "                }\n" +
                "            ],\n" +
                "            \"test_timestamps\": [\n" +
                "                {\n" +
                "                    \"word\": \"I\",\n" +
                "                    \"start_time\": 0.0,\n" +
                "                    \"end_time\": 0.8\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"used\",\n" +
                "                    \"start_time\": 0.8,\n" +
                "                    \"end_time\": 1.0999999999999999\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"to\",\n" +
                "                    \"start_time\": 1.0999999999999999,\n" +
                "                    \"end_time\": 1.3\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"rule\",\n" +
                "                    \"start_time\": 1.3,\n" +
                "                    \"end_time\": 1.5999999999999999\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"the\",\n" +
                "                    \"start_time\": 1.5999999999999999,\n" +
                "                    \"end_time\": 1.8\n" +
                "                },\n" +
                "                {\n" +
                "                    \"word\": \"world\",\n" +
                "                    \"start_time\": 1.8,\n" +
                "                    \"end_time\": 1.9999999999999998\n" +
                "                }\n" +
                "            ],\n" +
                "            \"ref_full_text\": \"I used to rule the world\",\n" +
                "            \"test_full_text\": \"I used to rule the world\",\n" +
                "            \"ref_pitch_data\": {\n" +
                "                \"times\": [\n" +
                "                    1.0,\n" +
                "                    1.02,\n" +
                "                    1.04,\n" +
                "                    1.06,\n" +
                "                    1.08,\n" +
                "                    1.1\n" +
                "                ],\n" +
                "                \"values\": [\n" +
                "                    158.00545546470602,\n" +
                "                    160.76403725322177,\n" +
                "                    161.57210369621407,\n" +
                "                    162.0977762763483,\n" +
                "                    164.05849262326186,\n" +
                "                    166.61401803342972\n" +
                "                ]\n" +
                "            },\n" +
                "            \"test_pitch_data\": {\n" +
                "                \"times\": [\n" +
                "                    1.8,\n" +
                "                    1.8133333333333335,\n" +
                "                    1.8266666666666667,\n" +
                "                    1.84,\n" +
                "                    1.8533333333333333,\n" +
                "                    1.8666666666666667,\n" +
                "                    1.88,\n" +
                "                    1.8933333333333333,\n" +
                "                    1.9066666666666665,\n" +
                "                    1.92,\n" +
                "                    1.9333333333333331,\n" +
                "                    1.9466666666666665,\n" +
                "                    1.9599999999999997,\n" +
                "                    1.9733333333333332,\n" +
                "                    1.9866666666666664,\n" +
                "                    1.9999999999999998\n" +
                "                ],\n" +
                "                \"values\": [\n" +
                "                    110.09348733359096,\n" +
                "                    109.36809882284035,\n" +
                "                    109.0931127725157,\n" +
                "                    109.83317802040898,\n" +
                "                    110.65633513649585,\n" +
                "                    109.75255804621177,\n" +
                "                    109.54621603762541,\n" +
                "                    108.63963440742643,\n" +
                "                    107.63459599956158,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    569.8429790008333,\n" +
                "                    573.7262408403313,\n" +
                "                    588.6762539155873\n" +
                "                ]\n" +
                "            },\n" +
                "            \"ref_intensity_data\": {\n" +
                "                \"times\": [\n" +
                "                    1.0,\n" +
                "                    1.025,\n" +
                "                    1.05,\n" +
                "                    1.0750000000000002,\n" +
                "                    1.1\n" +
                "                ],\n" +
                "                \"values\": [\n" +
                "                    69.92983267619553,\n" +
                "                    70.42163108137959,\n" +
                "                    71.0596426640062,\n" +
                "                    71.89228347771763,\n" +
                "                    73.28148320171752\n" +
                "                ]\n" +
                "            },\n" +
                "            \"test_intensity_data\": {\n" +
                "                \"times\": [\n" +
                "                    1.8,\n" +
                "                    1.8125,\n" +
                "                    1.825,\n" +
                "                    1.8375,\n" +
                "                    1.85,\n" +
                "                    1.8625,\n" +
                "                    1.875,\n" +
                "                    1.8875,\n" +
                "                    1.9,\n" +
                "                    1.9124999999999999,\n" +
                "                    1.9249999999999998,\n" +
                "                    1.9374999999999998,\n" +
                "                    1.9499999999999997,\n" +
                "                    1.9625,\n" +
                "                    1.9749999999999999,\n" +
                "                    1.9874999999999998,\n" +
                "                    1.9999999999999998\n" +
                "                ],\n" +
                "                \"values\": [\n" +
                "                    45.035375139427515,\n" +
                "                    43.31229334701622,\n" +
                "                    41.89715098267891,\n" +
                "                    40.85602590665403,\n" +
                "                    40.40985203363238,\n" +
                "                    39.988015408376164,\n" +
                "                    38.427057387245995,\n" +
                "                    37.14295718892845,\n" +
                "                    37.9659155987624,\n" +
                "                    42.618858934931936,\n" +
                "                    50.120072603700656,\n" +
                "                    54.33890168454734,\n" +
                "                    55.23424429643793,\n" +
                "                    54.01558786797817,\n" +
                "                    51.631386932288464,\n" +
                "                    49.00609845323653,\n" +
                "                    45.70113000051154\n" +
                "                ]\n" +
                "            },\n" +
                "            \"ref_formants_avg\": {\n" +
                "                \"F1\": [\n" +
                "                    803.6007622924807,\n" +
                "                    0.0,\n" +
                "                    565.4077028118852,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0\n" +
                "                ],\n" +
                "                \"F2\": [\n" +
                "                    2521.86612816032,\n" +
                "                    0.0,\n" +
                "                    1685.9278578498447,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0\n" +
                "                ]\n" +
                "            },\n" +
                "            \"test_formants_avg\": {\n" +
                "                \"F1\": [\n" +
                "                    634.082271381145,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0\n" +
                "                ],\n" +
                "                \"F2\": [\n" +
                "                    1844.185593866363,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0,\n" +
                "                    0.0\n" +
                "                ]\n" +
                "            }\n" +
                "        }"

        val value = mapper.readValue(json, EntityLyricAiAnalyze::class.java)
        pronounceRepository.save(
                LearnReportPronounce(
                        learnReportId = 4,
                        learnReportPronounceUserEn = "Before the coll done ran about, I will been give' it my best",
                        lyricSentenceEn = "test",
                        lyricSentenceKo = "test",
                        lyricAiAnalyze = value
                )
        )

    }
}