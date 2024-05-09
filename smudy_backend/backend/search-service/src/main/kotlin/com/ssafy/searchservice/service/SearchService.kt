package com.ssafy.searchservice.service

import com.ssafy.searchservice.entity.SongDocument
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val elasticsearchOperations: ElasticsearchOperations
) {

    fun searchAutoCreation(query: String): MutableList<SongDocument> {


        val mustCriteria = Criteria("song_name").`is`(query).boost(2.0f)
        val shouldCriteria = Criteria("song_lyrics").`is`(query)
        val criteria = mustCriteria.and(shouldCriteria)
        val criteriaQuery: Query = CriteriaQuery(criteria)

        return elasticsearchOperations.search(criteriaQuery, SongDocument::class.java)
            .map { it.content }
            .toList()
    }

    fun search(query: String, page: Int): MutableList<SongDocument> {

        val pageSize = 10 // 한 페이지 당 결과 수, 필요에 따라 조정 가능
        val from = (page - 1) * pageSize // 페이징을 위한 시작 인덱스 계산

        // 개별 Criteria 생성
        val mustQ = Criteria.where("song_name").`is`(query).boost(2.0f)
        val artistQ = Criteria.where("song_artist").`is`(query)
        val lyricsQ = Criteria.where("song_lyrics").`is`(query)
        val shouldQ = artistQ.or(lyricsQ)

        // boolean 쿼리 생성
        val booleanQ = mustQ.and(shouldQ)

        // 쿼리 객체를 생성, 페이징 설정을 추가
        val criteriaQuery: Query =
            CriteriaQuery(booleanQ).setPageable(PageRequest.of(from, pageSize))

        return elasticsearchOperations.search(criteriaQuery, SongDocument::class.java)
            .map { it.content }
            .toList()
    }
}