package com.teadone.IGM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.teadone.imgm.util.StreamGobbler; 
@Slf4j
@SpringBootTest
public class JythonTest {

	@Test
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
}
