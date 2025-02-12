package com.nightly.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
	/**
	 * 복합키 설정을 위한 복합키 클래스
	 * @EmbeddedId : 클래스 필드 ID로 포함됨을 명시
	 */
	@EmbeddedId
	private FollowId id;
	
    @ManyToOne
    @MapsId(value = "follower")
    @JoinColumn(name = "follower_email")
    private User follower;

    @ManyToOne
    @MapsId(value = "following")
    @JoinColumn(name = "following_email")
    private User following;

    private LocalDateTime followTime;

    @PrePersist
    protected void onCreate() {
        this.followTime = LocalDateTime.now();
    }
}
