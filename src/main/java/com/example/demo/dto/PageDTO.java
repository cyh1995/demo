package com.example.demo.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> question;
    private boolean showPrevious;
    private boolean showFirst;
    private boolean showNext;
    private boolean showLast;
    private Integer page;
    private List<Integer> pages = new LinkedList<>();
    private Integer totalPage;
    public void setPagination(Integer count, Integer page, Integer size) {

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
        this.page = page;
        pages.add(page);
        for(int i = 1;i <= 3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        if(page==1) {
            showPrevious = false;
        }
        else {
            showPrevious = true;
        }

        if(page.equals(totalPage)){
            showNext=false;
        }
        else{
            showNext =true;
        }

        if(pages.contains(1)){
            showFirst=false;
        }
        else {
            showFirst = true;
        }
        if(pages.contains(totalPage)){
            showLast = false;
        }
        else {
            showLast = true;
        }
    }
}
