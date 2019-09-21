package com.teadone.imgm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.python.jline.internal.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teadone.imgm.member.MemberService;
import com.teadone.imgm.member.MemberVO;

@Controller
public class MainController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping(value = { "/", "index" })
	public String home() {
		return "index";
	}
	@GetMapping(value = { "/login" })
	public String login() {
		return "login";
	}
	@RequestMapping(value="/loginPro" , method = RequestMethod.POST)
	public String loginPro(MemberVO vo,HttpSession session) {
		if(vo.getMemPw()==service.logIn(vo)) {
			Log.debug(vo.getMemId());
			session.setAttribute("user", vo.getMemId());
			return "index";
		}
		else {
			return "login";
		}
		
		
	}
}
