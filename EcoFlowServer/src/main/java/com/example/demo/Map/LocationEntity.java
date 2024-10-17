package com.example.demo.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "location")
public class LocationEntity {

    @Id
    @Column(name = "statId")
    private String statId;

    @Column(name = "statNm")
    private String statNm;

    @Column(name = "addr")
    private String addr;

    @Column(name = "location")
    private String location;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "useTime")
    private String useTime;

    @Column(name = "bnm")
    private String bnm;

    @Column(name = "busiNm")
    private String busiNm;

    @Column(name = "busiCall")
    private String busiCall;

    @Column(name = "zcode")
    private String zcode;

    @Column(name = "zscode")
    private String zscode;

    @Column(name = "kind")
    private String kind;

    @Column(name = "kindDetail")
    private String kindDetail;

    @Column(name = "parkingFree")
    private String parkingFree;

    @Column(name = "note")
    private String note;
    
    @Column(name = "stat")
    private String stat;

    public static LocationEntity toLocationEntity(LocationDTO locationDTO) {
        LocationEntity locationEntity = new LocationEntity();
        
        locationEntity.setStatNm(locationDTO.getStatNm());
        locationEntity.setStatId(locationDTO.getStatId());
        locationEntity.setAddr(locationDTO.getAddr());
        locationEntity.setLocation(locationDTO.getLocation());
        locationEntity.setLat(locationDTO.getLat());
        locationEntity.setLng(locationDTO.getLng());
        locationEntity.setUseTime(locationDTO.getUseTime());
        locationEntity.setBnm(locationDTO.getBnm());
        locationEntity.setBusiNm(locationDTO.getBusiNm());
        locationEntity.setBusiCall(locationDTO.getBusiCall());
        locationEntity.setZcode(locationDTO.getZcode());
        locationEntity.setZscode(locationDTO.getZscode());
        locationEntity.setKind(locationDTO.getKind());
        locationEntity.setKindDetail(locationDTO.getKindDetail());
        locationEntity.setParkingFree(locationDTO.getParkingFree());
        locationEntity.setNote(locationDTO.getNote());
        locationEntity.setStat(locationDTO.getStat());

        return locationEntity;
    }
}
