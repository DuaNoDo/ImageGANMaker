package com.teadone.imgm.test;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

@Service
public class TestPython {

	
	public void Test(){
		System.setProperty("python.cachedir.skip","true");
		PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("test.py");
        interpreter.exec("print(sum(7,8))");
	}
}
