package com.example.demo.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatDTO {
    private Integer id;
    private String statId;
    private String chgerId;
    private String chgerType;
    private String stat;
    private String lastTsdt;
    private String nowTsdt;
    private String output;
    private String method;
    private String limitYn;
    private String limitDetail;
}