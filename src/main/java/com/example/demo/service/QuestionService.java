package com.example.demo.service;


import com.example.demo.dto.PageDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.QuestionDO;
import com.example.demo.model.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {
@Autowired
    private UserMapper userMapper;
@Autowired
    private QuestionMapper questionMapper;

public PageDTO getQuestionDTO(Integer page, Integer size){
    PageDTO pageDTO = new PageDTO();
    int totalPage;
    int count = questionMapper.getPageCount();

    if(count%size==0){
        totalPage = count / size;
    }
    else {
        totalPage = count/size + 1;
    }

    if(page<1) {
        page=1;
    }
    if(page>=totalPage) {
        page=totalPage;
    }

    pageDTO.setPagination(totalPage,page);
    Integer offset = size*(page-1);
    List<QuestionDTO> questionDTOList = new LinkedList<>();
    List<QuestionDO> questionDOList = questionMapper.selectQuestion(offset,size);

    for (QuestionDO questionDO : questionDOList) {
        UserDO user = userMapper.findById(questionDO.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionDO,questionDTO);
        questionDTO.setUser(user);
        questionDTOList.add(questionDTO);
    }
    pageDTO.setQuestion(questionDTOList);

    return pageDTO;
}

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        int totalPage;
        int count = questionMapper.getPageCountByUserId(userId);

        if(count%size==0){
            totalPage = count / size;
        }
        else {
            totalPage = count/size + 1;
        }

        if(page<1) {
            page=1;
        }
        if(page>=totalPage) {
            page=totalPage;
        }
        pageDTO.setPagination(totalPage,page);
        Integer offset = size*(page-1);
        List<QuestionDTO> questionDTOList = new LinkedList<>();
        List<QuestionDO> questionDOList = questionMapper.findQuestionById(userId,offset,size);

        for (QuestionDO questionDO : questionDOList) {
            UserDO user = userMapper.findById(questionDO.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(questionDO,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestion(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getQuestionById(Integer qusetionId) {
        QuestionDTO questionDO = questionMapper.getQuestionById(qusetionId);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionDO,questionDTO);
        UserDO user = userMapper.findById(questionDTO.getCreator());

        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(QuestionDO question) {

        if(question.getId()==null) {

            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }
        else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }

    }
}
