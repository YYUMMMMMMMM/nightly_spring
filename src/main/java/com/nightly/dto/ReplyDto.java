package com.nightly.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private Integer number;
    private String content;

    private LocalDateTime writeTime;
    private LocalDateTime updateTime;

    private UserRequestDto user;
    private BoardDto board;
}
