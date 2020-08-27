package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.QuestionDO;
import com.example.demo.model.UserDO;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexController {
    //    @GetMapping("/index")
//    public String index(@RequestParam(name = "name",required=false, defaultValue="World")String name, Model model){
//        model.addAttribute("name",name);
//    return "index";
//}
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
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
        List<QuestionDTO> questionDTOList = questionService.getQuestionDTO();
        model.addAttribute("questionDTOS",questionDTOList);
        return "index";
    }



}

