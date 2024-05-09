package com.ssafy.searchservice.repository

import com.ssafy.searchservice.entity.SongDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

interface SongDocumentRepository: ElasticsearchRepository<SongDocument, Long> {
}