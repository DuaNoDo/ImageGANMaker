package com.teadone.imgm.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.teadone.imgm.image.ImageService;
import com.teadone.imgm.image.ImageVO;
import com.teadone.imgm.member.MemberService;
import com.teadone.imgm.member.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@Autowired
	private MemberService service;
	@Autowired
	private ImageService imgservice;
	@GetMapping(value = { "/", "index" })
	public String home() {
		return "index";
	}
	@GetMapping(value = { "/login" })
	public String login() {
		return "login";
	}
	@GetMapping(value = { "/regist" })
	public String register() {
		return "regist";
	}
	@RequestMapping(value="/loginPro" , method = RequestMethod.POST)
	public String loginPro(MemberVO vo,HttpSession session) {
		if(vo.getMemPw().equals(service.logIn(vo))) {
			session.setAttribute("user", vo.getMemId());	
			return "redirect:/";
		}
		else {
			return "login";
		}
	}
	@RequestMapping(value="/registPro" , method=RequestMethod.POST)
	public String regist(MemberVO vo) {
		if(service.join(vo)==1) {
			return "login";
		}
		else {
			return "regist";
		}
	}
	@GetMapping(value="logout")
	public String logout(HttpSession session) {
		session.invalidate();	
		return "redirect:/";
	}
	@GetMapping(value="upload")
	public String upload(HttpSession session) {
		if(session.getAttribute("user") != null) {
			return "upload";
		}
		else
			return "login";
	}
	@RequestMapping(value="/UploadPro" , method= {RequestMethod.POST,RequestMethod.GET})
	public String fileUpload(MultipartHttpServletRequest multipartHttpServletRequest,HttpSession session) {
		if(session!=null) {
			ImageVO vo=new ImageVO();
			vo.setMemId(session.getAttribute("user").toString());
			log.debug(Integer.toString(imgservice.fileUpload(multipartHttpServletRequest , vo)));
			
			return "redirect:/";
		}
		else
			return "login";
	}
}
