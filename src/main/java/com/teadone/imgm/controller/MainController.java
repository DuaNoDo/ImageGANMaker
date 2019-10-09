package com.teadone.imgm.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.teadone.imgm.board.BoardService;
import com.teadone.imgm.image.ImageService;
import com.teadone.imgm.image.ImageVO;
import com.teadone.imgm.member.MemberService;
import com.teadone.imgm.member.MemberVO;
import com.teadone.imgm.util.GetFileList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@Autowired
	private MemberService service;
	@Autowired
	private ImageService imgservice;
	@Autowired
	private BoardService boardservice;
	@GetMapping(value = { "/", "index" })
	public String home(ModelMap modelmap) throws IOException {
		List<String> f=GetFileList.getImgFileList("D:\\SpringWorks\\ImageGANMaker\\src\\main\\resources\\static\\Generate");
		modelmap.put("gImg",f);
		
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
	@GetMapping(value="myImage")
	public String myImage(ModelMap model,HttpSession session) {
		ImageVO vo=new ImageVO();
		vo.setMemId(session.getAttribute("user").toString());
		model.put("gmImg", imgservice.getImageList(vo));
		
		return "myImage";
	}
	
	@GetMapping(value="board")
	public String board(ModelMap model) {
		
		model.put("post", boardservice.getPosts());
		return "board";
	}
	@GetMapping(value="boardWrite")
	public String boardWrite(HttpSession session) {
		if(session.getAttribute("user") != null) {
			return "boardWirte";
		}
		else
			return "login";
	}
}
