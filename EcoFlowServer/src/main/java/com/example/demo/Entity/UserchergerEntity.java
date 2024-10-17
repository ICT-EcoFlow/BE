package com.example.demo.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.example.demo.DTO.UserchergerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;


@Table(name= "usercherger")
@Getter
@Setter
@Entity
public class UserchergerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "username")
    private String username;

    @Column(name = "statNm")
    private String statNm;

    @Column(name = "addr")
    private String addr;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "useTime")
    private String useTime;

    @Column(name = "busiCall")
    private String busiCall;

    @Column(name = "chgerType")
    private String chgerType;

    @Column(name = "stat")
    private String stat;


public static UserchergerEntity touserchergerEntity(UserchergerDTO userchergerDTO) {
	UserchergerEntity userchergerEntity = new UserchergerEntity();
	
	    userchergerEntity.setId(userchergerDTO.getId());
	    userchergerEntity.setUsername(userchergerDTO.getUsername());
	    userchergerEntity.setStatNm(userchergerDTO.getStatNm());
	    userchergerEntity.setAddr(userchergerDTO.getAddr());
	    userchergerEntity.setLat(userchergerDTO.getLat());
	    userchergerEntity.setLng(userchergerDTO.getLng());
	    userchergerEntity.setUseTime(userchergerDTO.getUseTime());
	    userchergerEntity.setBusiCall(userchergerDTO.getBusiCall());
	    userchergerEntity.setChgerType(userchergerDTO.getChgerType());
	    userchergerEntity.setStat(userchergerDTO.getStat());

	return userchergerEntity;
}

}