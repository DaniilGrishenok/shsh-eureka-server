package com.shsh.eureka_server.repository;

import com.shsh.eureka_server.models.ConfirmationWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationWordRepository extends JpaRepository<ConfirmationWord, Long> {
    boolean existsByWord(String word);
}
