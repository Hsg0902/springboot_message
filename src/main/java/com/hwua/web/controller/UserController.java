package com.hwua.web.controller;

import com.hwua.pojo.User;
import com.hwua.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/logout")
    public String exit(HttpServletRequest req){
        //先把session销毁
        req.getSession().invalidate();
        //回到登录页面
        return "index";
    }

    @GetMapping("/queryAllUsers/{sendId}")
    public ModelAndView queryAllUsers(@PathVariable("sendId") String sendId) throws Exception{
        ModelAndView mv = new ModelAndView("newMsg");
        System.out.println(sendId);
        mv.addObject("sendId",sendId);
        List<User> uList = userService.getAllUsers();
        mv.addObject("uList",uList);
        return mv;
    }

    @GetMapping("/queryAllUsers")
    public ModelAndView queryAllUsers() throws Exception{
        ModelAndView mv = new ModelAndView("newMsg");
        mv.addObject("sendId",1);
        List<User> uList = userService.getAllUsers();
        mv.addObject("uList",uList);
        return mv;

    }
}
