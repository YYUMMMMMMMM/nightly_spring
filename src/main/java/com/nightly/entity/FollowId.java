package com.nightly.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Follow 엔티티의 복합키 설정을 위한 클래스
 * Serializable -> 직렬화 인터페이스 : 데이터 전송 및 캐싱 시 필수
 * 
 * @Embeddable : DB에 별도로 테이블이 존재하지 않음 & 클래스에 포함될 수 있음을 명시
 */
@Embeddable
public class FollowId implements Serializable{
	private String follower;
	private String following;
	

	// 기본 생성자 작성
    public FollowId() {}
    
	public FollowId(String follower, String following) {
		this.follower = follower;
		this.following = following;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId that = (FollowId) o;
        return Objects.equals(follower, that.follower) &&
               Objects.equals(following, that.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, following);
    }
}
