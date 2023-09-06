package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true)
    private Long id;

    // 기사 제목
    @Column(updatable = true)
    private String title;

    // 기사 URL
    @Column(updatable = true)
    private String url;

    // 미리보기
    @Column(updatable = true)
    private String preview;
}
