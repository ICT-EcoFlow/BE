package com.example.demo.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Getter
@Setter
@ToString
@Entity
@Table(name = "stat")
public class StatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "statId")
    private String statId;

    @Column(name = "chgerId")
    private String chgerId;

    @Column(name = "chgerType")
    private String chgerType;

    @Column(name = "stat")
    private String stat;

    @Column(name = "lastTsdt")
    private String lastTsdt;

    @Column(name = "nowTsdt")
    private String nowTsdt;

    @Column(name = "output")
    private String output;

    @Column(name = "method")
    private String method;

    @Column(name = "limitYn")
    private String limitYn;

    @Column(name = "limitDetail")
    private String limitDetail;

    public static StatEntity toStatEntity(StatDTO statDTO) {
        StatEntity statEntity = new StatEntity();
        
        statEntity.setId(statDTO.getId());
        statEntity.setStatId(statDTO.getStatId());
        statEntity.setChgerId(statDTO.getChgerId());
        statEntity.setChgerType(statDTO.getChgerType());
        statEntity.setStat(statDTO.getStat());
        statEntity.setLastTsdt(statDTO.getLastTsdt());
        statEntity.setNowTsdt(statDTO.getNowTsdt());
        statEntity.setOutput(statDTO.getOutput());
        statEntity.setMethod(statDTO.getMethod());
        statEntity.setLimitYn(statDTO.getLimitYn());
        statEntity.setLimitDetail(statDTO.getLimitDetail());

        return statEntity;
    }
}