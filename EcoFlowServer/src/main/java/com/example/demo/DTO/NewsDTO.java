package com.example.demo.DTO;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class NewsDTO {


    private String title;
    private String link;
    private String description;
    private String pubDate;

}