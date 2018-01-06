package cn.et;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;


@Controller
public class SendController {

	@Autowired
	private IsendMail sendMail;
	
	@GetMapping("/sendClient")
	public String send(String email_to,String email_subject ,String email_content){
		//调用email服务
		String controller="/send";
		//通过注册中心客户端负载均衡  获取一台主机来调用
		try {
			controller += "?email_to="+email_to+"&email_subject="+email_subject+"&email_content="+email_content;
			//String result=restTemplate.getForObject("http://EMAILSERVER"+controller, String.class);
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/error.html";
		}
		return "redirect:/suc.html";
	}
	/**
	 * 演示post
	 * @param email_to
	 * @param email_subject
	 * @param email_content
	 * @return
	 */
	@PostMapping("/sendClientpost")
	public String postsend(String email_to,String email_subject ,String email_content){
		
		try {
		
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("email_to",email_to );
			map.put("email_subject", email_subject);
			map.put("email_content", email_content);
			
			sendMail.send(map);
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/error.html";
		}
		return "redirect:/suc.html";
	}
	/**
	 * 演示调用sendmail的/user/这个请求
	 * @return
	 */
	@ResponseBody
	@GetMapping("/invokeUser")
	public String invokeUser(String id){
		
		Map map=sendMail.getUser(id);
		return map.get("name").toString();
	}
}
