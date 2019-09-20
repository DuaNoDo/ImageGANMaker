package com.teadone.imgm.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Controller
public class MainController {
	
	@GetMapping(value = { "/", "index" })
	public String home() {
		return "index";
	}

}
