package com.teadone.imgm.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetFileList {
	
	
    /**
     * 해당 경로의 이미지 파일 목록 반환 
     * @throws IOException 
     */
    public static List<String> getImgFileList(String path) throws IOException{
    	
    	return Files.walk(Paths.get(path))
    		.filter(Files::isRegularFile)
    		.filter(p -> {
    			String fName = p.toFile().getName().toLowerCase();    
    			return fName.endsWith(".jpg") || fName.endsWith(".png");
    		})
        	.map(p ->{
        		String absPath = p.toAbsolutePath().toString();
        		//replcae 
        		
        		return absPath.replace("D:\\SpringWorks\\ImageGANMaker\\src\\main\\resources\\static\\", "");
        	})
    		.collect(Collectors.toList());            
    }
}
