package com.spring.controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;  
import javax.servlet.http.HttpServlet;  
import org.apache.log4j.Logger;   
import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.context.ContextLoader;  
import org.springframework.web.context.WebApplicationContext;  

import com.spring.model.User;
import com.spring.service.UserService;
 
  
@Controller  
@RequestMapping(value="/user")  
public class LoginController extends HttpServlet{          
    
	@Resource 
    private UserService userService;  
      
  
    //@RequestMapping(value="/login")   //����༶��������ӳ���ַ��/user�����˴������������ַ��/user/login  
    public String doLogin(Model model,Object loginForm){  
        //model.addAttribute("username",username);  //model��������װ���ݣ���ǰ̨����  
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext sc = context.getServletContext();            
        return "/welcome";  
    }  
      
    @RequestMapping(value="/login")  
    public String login(@ModelAttribute User user,Model model){    
        model.addAttribute(user);  
        return "/welcome";  
    }  
      
    @RequestMapping(value="/save")  
    public String save(@ModelAttribute User user,Model model){         
        userService.saveUser(user);  
        model.addAttribute(user);  
        return "/welcome";  
    }  
}  