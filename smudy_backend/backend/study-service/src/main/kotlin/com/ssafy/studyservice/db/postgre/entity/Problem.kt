package com.ssafy.studyservice.db.postgre.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.Modifying

@Entity
@Table(name = "problem")
class Problem(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "problem_id", nullable = false)
        val problemId: Int,

        @Column(name = "problem_box_id", nullable = false)
        val problemBoxId: Int,

        @Column(name = "lyric_id", nullable = false)
        val lyricId: Int,

        @Column(name = "lyric_sentence_en", nullable = false, columnDefinition = "TEXT")
        val sentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = true, columnDefinition = "TEXT")
        var sentenceKo: String,

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "problem_box_id", referencedColumnName = "problem_box_id", insertable = false, updatable = false)
//        val problemBox: ProblemBox
) {

        fun changesSentenceKo(translate: String) {
                sentenceKo = translate
        }
}
