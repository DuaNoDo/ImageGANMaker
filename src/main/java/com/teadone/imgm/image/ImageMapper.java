package com.teadone.imgm.image;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartHttpServletRequest;
@Mapper
public interface ImageMapper {
	public int fileUpload(MultipartHttpServletRequest multipartHttpServletRequest,ImageVO vo);
	public int  insertImage(ImageVO vo);
}
