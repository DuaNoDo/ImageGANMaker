package com.teadone.imgm.image;

import java.io.File;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartHttpServletRequest;
@Mapper
public interface ImageMapper {
	public int fileUpload(MultipartHttpServletRequest multipartHttpServletRequest,ImageVO vo);
	public int  insertImage(ImageVO vo);
	public List<String> getImageList(ImageVO vo);
	//public List<String> getRecentImageList(String path, ImageVO vo);
}
