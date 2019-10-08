package com.teadone.imgm.image;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ImageVO {
	private String ImgNum;
	private String MemId;
	private String[] MemImg;
	private String OriginalName;
	private String SaveName;
	private Timestamp RegTime;
}
