package com.example.demo.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.example.demo.Entity.UserchergerEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class UserchergerDTO {

	private Integer id;
	private String username;
	private String statNm;
	private String addr;
	private String lat;
	private String lng;
	private String useTime;
	private String busiCall;
	private String chgerType;
	private String stat;


public static UserchergerDTO toUserchergerDTO(UserchergerEntity userchergerEntity) {
	UserchergerDTO userchergerDTO = new UserchergerDTO();
	
	userchergerDTO.setId(userchergerEntity.getId());
	userchergerDTO.setUsername(userchergerEntity.getUsername());
	userchergerDTO.setStatNm(userchergerEntity.getStatNm());
	userchergerDTO.setAddr(userchergerEntity.getAddr());
	userchergerDTO.setLat(userchergerEntity.getLat());
	userchergerDTO.setLng(userchergerEntity.getLng());
	userchergerDTO.setUseTime(userchergerEntity.getUseTime());
	userchergerDTO.setBusiCall(userchergerEntity.getBusiCall());
	userchergerDTO.setChgerType(userchergerEntity.getChgerType());
	userchergerDTO.setStat(userchergerEntity.getStat());


	return userchergerDTO;
}


}
