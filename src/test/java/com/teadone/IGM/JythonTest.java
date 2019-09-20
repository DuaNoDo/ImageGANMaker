package com.teadone.IGM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.clearInvocations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JythonTest {

	@Test
	public void contextLoads() throws IOException {
		
		/*
		 * String command =
		 * "python /c start python D:\\SpringWorks\\ImageGANMaker\\src\\test\\java\\com\\teadone\\IGM\\test.py"
		 * ; Process p = Runtime.getRuntime().exec(command);
		 */
		
		 //Process p =Runtime.getRuntime().exec("python D:/SpringWorks/ImageGANMaker/src/test/java/com/teadone/IGM/test.py"); 
		 Process p =Runtime.getRuntime().exec("python test.py");
		 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		 String ret = in.readLine(); 
		 System.out.println("value is : "+ret);
		 
	}	
}
