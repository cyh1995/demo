package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.QuestionDO;
import com.example.demo.model.UserDO;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId")Integer questionId,Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(questionId);
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",questionDTO.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            @RequestParam(value = "id",required = false)Integer id,
                            HttpServletRequest request,
                            Model model){
        UserDO userDO = (UserDO)request.getSession().getAttribute("user");
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
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
