package com.teadone.IGM;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.teadone.imgm.util.GetFileList;
import com.teadone.imgm.util.StreamGobbler;

import lombok.extern.slf4j.Slf4j; 

@Slf4j
@SpringBootTest
public class JythonTest {

	
	
	public void contextLoads() throws IOException {
		
		 //Process p =Runtime.getRuntime().exec("python D:/SpringWorks/ImageGANMaker/src/test/java/com/teadone/IGM/test.py"); 
		 
		 //Runtime runtime=Runtime.getRuntime();
		 //runtime.exec("cmd.exe /c start C:/test.bat"); 
		 //runtime.exec("cmd.exe /c start C:/test2.bat");
		 
		 String cmd = "cmd.exe /c start test.bat";
		 
		 Runtime run = Runtime.getRuntime();
		   Process p = null;
		   
		   try{
		    p = run.exec(cmd);
		    StreamGobbler gb1 = new StreamGobbler(p.getInputStream());
		    StreamGobbler gb2 = new StreamGobbler(p.getErrorStream());
		    gb1.start();
		    gb2.start();
		    
		      while (true) {
		        if (!gb1.isAlive() && !gb2.isAlive()) {  //두개의 스레드가 정지할면 프로세스 종료때까지 기다린다.
		         System.out.println("Thread gb1 Status : "+gb1.getState());
		         System.out.println("Thread gb2 Status : "+gb1.getState());
		         p.waitFor();
		         break;
		        }
		    }
		   }catch(Exception e){
		    log.debug(e.toString());
		   }finally{
		    if(p != null) p.destroy();
		   }
	}	
	
	@Test
	public void ImageGetListTest() throws IOException{
		log.info("HELLO WORLD");
		for(String f :  GetFileList.getImgFileList("D:\\SpringWorks\\ImageGANMaker\\src\\main\\resources\\static\\Generate")) {
			log.info(f.toString());
		};
	}
}
