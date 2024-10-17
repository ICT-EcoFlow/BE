package com.example.demo.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Integer> {
    // statId를 기준으로 데이터 검색
    List<StatEntity> findByStatId(String statId);
    
    List<StatEntity> findByStatIdIn(Set<String> statIdSet);
}