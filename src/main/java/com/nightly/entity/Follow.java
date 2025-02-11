package com.nightly.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @ManyToOne
    @JoinColumn(name = "follower_email")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_email")
    private User following;

    private LocalDateTime followTime;

    @PrePersist
    protected void onCreate() {
        this.followTime = LocalDateTime.now();
    }
}
