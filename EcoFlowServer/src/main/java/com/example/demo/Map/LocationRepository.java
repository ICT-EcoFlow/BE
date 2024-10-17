package com.example.demo.Map;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {
    @Query("SELECT l FROM LocationEntity l WHERE l.statNm LIKE %:statNm%")
    List<LocationEntity> findByStatNmContaining(@Param("statNm") String statNm);
    
    @Query("SELECT l FROM LocationEntity l WHERE l.lat BETWEEN :minLat AND :maxLat AND l.lng BETWEEN :minLon AND :maxLon")
    List<LocationEntity> findLocationsWithinBounds(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                                   @Param("minLon") double minLon, @Param("maxLon") double maxLon);
}