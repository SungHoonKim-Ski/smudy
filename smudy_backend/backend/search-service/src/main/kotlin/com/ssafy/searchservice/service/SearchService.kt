package com.ssafy.searchservice.service

import com.ssafy.searchservice.entity.SongDocument
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.data.elasticsearch.core.query.StringQuery
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val elasticsearchOperations: ElasticsearchOperations
) {

    fun searchAutoCreation(query: String): MutableList<SongDocument> {

        val sQuery: Query = StringQuery("""
            {
                "bool": {
                    "must": [
                        {
                            "match": {
                                "song_name": {
                                    "query": "$query",
                                    "boost": 2
                                }
                            }
                        }
                    ],
                    "should": [
                        {
                            "match": {
                                "song_lyrics": "$query"
                            }
                        }
                    ],
                    "minimum_should_match": 1
                }
            }
        """
        )

        return elasticsearchOperations.search(sQuery, SongDocument::class.java)
            .map { it.content }
            .toList()
    }

    fun search(query: String, page: Int): MutableList<SongDocument> {

        val pageSize = 10 // 한 페이지 당 결과 수, 필요에 따라 조정 가능
        val from = (page - 1) * pageSize // 페이징을 위한 시작 인덱스 계산

        val sQuery: Query = StringQuery("""
            {
                "bool": {
                    "must": [
                        {
                            "match": {
                                "song_name": {
                                    "query": "$query",
                                    "boost": 2
                                }
                            }
                        }
                    ],
                    "should": [
                        {
                            "match": {
                                "song_lyrics": "$query"
                            }
                        }
                    ],
                    "minimum_should_match": 1
                }
            },
            "from": $from,
            "size": $pageSize
        """)

        return elasticsearchOperations.search(sQuery, SongDocument::class.java)
            .map { it.content }
            .toList()
    }
}