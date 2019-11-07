package com.teadone.imgm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.teadone.imgm.board.BoardService;
import com.teadone.imgm.board.BoardVO;
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
	@Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final String ImgDir="D:\\SpringWorks\\ImageGANMaker\\src\\main\\resources\\static\\Generate";
    
		
	
	@GetMapping(value = { "/", "index" })
	public String home(ModelMap modelmap) throws IOException {
		List<String> f = GetFileList.getImgFileList(ImgDir);
		modelmap.put("gImg", f);

		return "index";
	}

	@GetMapping(value = { "/login" })
	public String login(Model model) {
		Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
            .as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
     		
		return "login";
	}
	@GetMapping("/loginSuccess")
    public String getLoginInfo(HttpSession session, OAuth2AuthenticationToken authentication) {
		String email=null;
		String name=null;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            email=userAttributes.get("email").toString();
            name =userAttributes.get("name").toString();
            log.debug(userAttributes.toString());
            MemberVO vo=new MemberVO();
            vo.setMemId(email);
            vo.setMemName(name);
            
            if(service.duplicateCheckMem(vo)==1) {
            	session.setAttribute("user", userAttributes.get("email"));
            	session.setAttribute("userAtt", userAttributes);
            }
            else {
            	service.join(vo);
                session.setAttribute("user", userAttributes.get("email"));
                session.setAttribute("userAtt", userAttributes);
            }
            
            
        }

        return "redirect:/";
        
	}

	@GetMapping(value = { "/regist" })
	public String register() {
		return "regist";
	}

	@RequestMapping(value = "/registPro", method = RequestMethod.POST)
	public String regist(MemberVO vo) {
		if (service.join(vo) == 1) {
			return "login";
		} else {
			return "regist";
		}
	}

	@GetMapping(value = "logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}

	@GetMapping(value = "upload")
	public String upload(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "upload";
		} else
			return "login";
	}

	@RequestMapping(value = "UploadPro", method = { RequestMethod.POST, RequestMethod.GET })
	public String fileUpload(MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session) {
		if (session.getAttribute("user") != null) {
			ImageVO vo = new ImageVO();
			vo.setMemId(session.getAttribute("user").toString());
			log.debug(Integer.toString(imgservice.fileUpload(multipartHttpServletRequest, vo)));

			return "redirect:/";
		} else
			return "login";
	}

	@GetMapping(value = "myImage")
	public String myImage(ModelMap model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			ImageVO vo = new ImageVO();
			vo.setMemId(session.getAttribute("user").toString());
			model.put("gmImg", imgservice.getImageList(vo));

			return "myImage";
		} else {
			return "login";
		}
	}

	@GetMapping(value = "board")
	public String board(ModelMap model) {
		
		model.put("post", boardservice.getPosts());
		return "board";
	}

	@GetMapping(value = "boardWrite")
	public String boardWrite(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "boardWrite";
		} else
			return "login";
	}

	@RequestMapping(value = "writePost", method = { RequestMethod.POST, RequestMethod.GET })
	public String writePost(MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session, BoardVO vo) {
		if (session != null) {
			vo.setMemId(session.getAttribute("user").toString());

			return "redirect:/";
		} else
			return "login";
	}

//	@GetMapping(value = "recentImage")
//	public String recentImage(ModelMap model, HttpSession session) {
//		model.put("gmImg", imgservice.getRecentImageList());
//		List<String> f = GetFileList.getImgFileList(ImgDir);
		
//		return "recentImage";
//	}

	@GetMapping(value = "myGenerateImage")
	public String myGenerateImage(ModelMap model, HttpSession session) throws IOException {
		if (session != null) {
			ImageVO vo = new ImageVO();
			vo.setMemId(session.getAttribute("user").toString());
			model.put("gmImg",GetFileList.getRecentFileList(ImgDir,imgservice.getImageList(vo)));
			return "myGenerateImage";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "board/{num}")
	public String getContent(@PathVariable int num, ModelMap model) {
		BoardVO vo = new BoardVO();
		vo.setNum(num);
		model.put("post", boardservice.getPost(vo));

		return "content";
	}

	@RequestMapping(value = "personalInform")
	public String personalInform() {
		return "personalInform";
	}
	
	@RequestMapping(value = "mypage")
	public String mypage(HttpSession session, ModelMap model) {
		log.debug(session.getAttribute("userAtt").toString());
		model.addAttribute("userAtt",session.getAttribute("userAtt"));
		
		return "mypage";
		
	}
}
