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
    int count = questionMapper.getPageCount();
    pageDTO.setPagination(count,page,size);
    if(page<1) {
        page=1;
    }
    if(page>=pageDTO.getTotalPage()) {
        page=pageDTO.getTotalPage();
    }
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
}
