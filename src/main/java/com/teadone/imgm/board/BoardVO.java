package com.teadone.imgm.board;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {

	private String num;
	private String title;
	private String content;
	private String MemId;
	private int read;
	private String OriginalName;
	private String SaveName;
	private Timestamp RegTime;
}
