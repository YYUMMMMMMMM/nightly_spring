package com.nightly.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Integer number;

    private String title;

    private String content;

    private LocalDateTime writeTime;
    private LocalDateTime updateTime;

    private int favoriteCount;
    private int replyCount;
    private int viewCount;

    private UserRequestDto user;
}
