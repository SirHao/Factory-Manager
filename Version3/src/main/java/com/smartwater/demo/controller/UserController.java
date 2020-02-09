
package com.smartwater.demo.controller;

import com.smartwater.demo.config.WebSecurityConfig;
import com.smartwater.demo.domain.User;
import com.smartwater.demo.service.UserValidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserController
{
    @Autowired UserValidateService userValidateService;

    @RequestMapping("/login")
    public String createUserForm(ModelMap map)
    {
        map.addAttribute("user",new User());
        map.addAttribute("action","create");
        return "login";
    }


//    @RequestMapping(value = "/postform", method = RequestMethod.POST)
//    public String postUser( @Valid User user, HttpSession session, ModelMap map) {
//
//        if (userValidateService.validatebyName(user.getUsername(), user.getPassword()) == 1)   //登录校验
//        {
//            session.setAttribute(WebSecurityConfig.SESSION_KEY, user.getUsername());  //保存session
//            System.out.println("登陆成功");
//            return "redirect:/main";                                            //跳转到主界面
//        } else {
//            return "login";                                                    // 校验失败重定向会login.html
//        }
//    }

    @RequestMapping(value = "/postform", method = RequestMethod.POST)
    public String loginCheck(HttpServletRequest request,
                             @RequestParam(value = "kaptcha", required = true) String kaptchaReceived,
                             @Valid User user, HttpSession session, ModelMap map){
        //用户输入的验证码的值
        String kaptchaExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        //校验验证码是否正确
        if (kaptchaReceived == null || !kaptchaReceived.equals(kaptchaExpected)) {
            System.out.println("===============================");
            return "login";//返回验证码错误
        }
        if (userValidateService.validatebyName(user.getUsername(), user.getPassword()) == 1)   //登录校验
        {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, user.getUsername());  //保存session
            System.out.println("登陆成功");
            return "redirect:/main";                                            //跳转到主界面
        } else {
            System.out.println("==+++++++++++++++++==============");
            return "login";                                                    // 校验失败重定向会login.html
        }


    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    public String out(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);                 //销毁SESSION
        return "redirect:/login";
    }

}
