package com.ly.spring.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Account {
    private String cardNo;
    private String name;
    private int money;
}
