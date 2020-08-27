package com.example.demo.controller;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.QuestionDO;
import com.example.demo.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            HttpServletRequest request,
                            Model model){

        Cookie[] cookies = request.getCookies();
        UserDO userDO=null;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    userDO = userMapper.findByToken(token);
                    if (userDO != null) {
                        request.getSession().setAttribute("user", userDO);
                    }
                    break;
                }
            }
        }
        if(userDO==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null|| "".equals(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null|| "".equals(description)){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(tag==null|| "".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        QuestionDO question = new QuestionDO();
        question.setDescription(description);
        question.setTitle(title);
        question.setTag(tag);
        question.setCreator(userDO.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
