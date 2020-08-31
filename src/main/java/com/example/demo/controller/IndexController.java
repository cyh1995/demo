package com.example.demo.controller;

import com.example.demo.dto.PageDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserDO;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
        @RequestParam(name = "size",defaultValue = "5")Integer size) {
            Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserDO userDO = userMapper.findByToken(token);
                    if (userDO != null) {
                        request.getSession().setAttribute("user", userDO);
                    }
                    break;
                }

            }
        }
        PageDTO pageDTO = questionService.getQuestionDTO(page,size);
        model.addAttribute("pageDTO",pageDTO);
        return "index";
    }



}

