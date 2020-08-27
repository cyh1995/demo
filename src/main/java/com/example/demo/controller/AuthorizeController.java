package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserDO;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null&&githubUser.getId()!=null) {
            UserDO userDO = new UserDO();
            String token = UUID.randomUUID().toString();
            userDO.setToken(token);
            userDO.setName(githubUser.getName());
            userDO.setAccessId(String.valueOf(githubUser.getId()));
            userDO.setAvatarUrl(githubUser.getAvatarUrl());
            userDO.setGmtCreate(System.currentTimeMillis());
            userDO.setGmtModified(userDO.getGmtCreate());
            userMapper.insert(userDO);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        } else {
            return "redirect:/";
        }

    }

}
