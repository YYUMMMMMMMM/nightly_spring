package com.nightly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightly.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
    
}
