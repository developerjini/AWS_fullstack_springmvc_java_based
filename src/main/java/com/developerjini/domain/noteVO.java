package com.developerjini.domain;

import java.util.Date;

import lombok.Data;

@Data
public class noteVO {
	private Long noteno;
	private String sender;
	private String receiver;
	private Date sdate;
	private Date rdate;
	private String message;
}
