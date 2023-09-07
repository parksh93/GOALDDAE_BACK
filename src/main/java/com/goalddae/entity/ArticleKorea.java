package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleKorea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true)
    private Long id;

    // 국내 기사 제목
    @Column(updatable = true)
    private String title;

    // 국내 기사 URL
    @Column(updatable = true)
    private String url;

    // 미리보기
    @Column(updatable = true)
    private String preview;

    // 생성된 시간
    @Column(updatable = true)
    @CreationTimestamp // 현재시간
    private LocalDateTime createdAt;
}
