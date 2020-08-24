package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class indexController {
    //    @GetMapping("/index")
//    public String index(@RequestParam(name = "name",required=false, defaultValue="World")String name, Model model){
//        model.addAttribute("name",name);
//    return "index";
//}
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                UserDO userDO = userMapper.findByToken(token);
                if(userDO!=null){
                    request.getSession().setAttribute("user",userDO);
                }
                break;
            }

        }


        return "index";
    }



}

