package com.teadone.IGM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@SpringBootTest
public class JythonTest {

	@Test
	public void contextLoads() throws IOException {
		
		 //Process p =Runtime.getRuntime().exec("python D:/SpringWorks/ImageGANMaker/src/test/java/com/teadone/IGM/test.py"); 
		 Process p =Runtime.getRuntime().exec("C:/test.bat");
		 log.debug("test.py");
		 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		 String ret = in.readLine(); 
		 System.out.println("value is : "+ret);
		 
	}	
}
