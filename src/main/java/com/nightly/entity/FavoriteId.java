package com.nightly.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * 사용자 이메일 주소와 게시글의 번호를 복합키로 설정
 */
@Embeddable
public class FavoriteId implements Serializable {
	private String userEmail;
	private String boardNumber;
	
	public FavoriteId() {}
	
	public FavoriteId (String userEmail, String boardNumber) {
		this.userEmail = userEmail;
		this.boardNumber = boardNumber;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userEmail, that.userEmail) &&
               Objects.equals(boardNumber, that.boardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, boardNumber);
    }
}
