package com.ssafy.data.datasource.music

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.AddStudyListRequest
import com.ssafy.data.model.music.KeywordResponse

interface MusicRemoteDataSource {
    suspend fun searchKeyword(query:String):Result<DefaultResponse<KeywordResponse>>
    suspend fun addStudyList(studyList: AddStudyListRequest):Result<DefaultResponse<Boolean>>
}