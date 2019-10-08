package com.teadone.imgm.image;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.teadone.imgm.util.GetFileList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService implements ImageMapper {
	@Autowired
	ImageMapper mapper;

	public int fileUpload(MultipartHttpServletRequest multipartHttpServletRequest, ImageVO vo) {
		// 파일경로
		int sum=0;
		String filePath = "E:\\Upload\\";
		// 파일들을 List형식으로 보관
		List<MultipartFile> MemImg = multipartHttpServletRequest.getFiles("MemImg");

		File file = new File(filePath);
		// 파일이 없다면 디렉토리를 생성
		if (file.exists() == false) {
			file.mkdirs();
		}

		if (MemImg.size() == 1 && MemImg.get(0).getOriginalFilename().equals("")) {

		} else {
			for (int i = 0; i < MemImg.size(); i++) {
				// 파일 중복명 처리
				String genId = UUID.randomUUID().toString();
				// 본래 파일명
				String originalfileName = MemImg.get(i).getOriginalFilename();

				String saveFileName = genId + "." + FilenameUtils.getExtension(originalfileName);
				// 저장되는 파일 이름

				String savePath = filePath + saveFileName; // 저장 될 파일 경로

				try {
					MemImg.get(i).transferTo(new File(savePath)); // 파일 저장
				} catch (IOException ie) {
					log.debug(ie.toString());
				}
				vo.setOriginalName(originalfileName);
				vo.setSaveName(saveFileName);
				sum+= insertImage(vo);
			}
		}
		try {
			Process process=Runtime.getRuntime().exec("cmd.exe /c start test.bat");
		} catch (IOException e) {
			log.debug(e.toString());
			e.printStackTrace();
		}
		
		return sum;
	}
	
	@Override
	public int insertImage(ImageVO vo) {

		return mapper.insertImage(vo);
	}

	@Override
	public List<String> getImageList(ImageVO vo) {
		
		return mapper.getImageList(vo);
	}
	

}
