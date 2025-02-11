package com.nightly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column(columnDefinition = "text")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "board_number")
    private Board board;
}
