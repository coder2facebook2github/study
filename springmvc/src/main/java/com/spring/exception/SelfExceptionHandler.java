package com.spring.exception;

import com.spring.domain.sys.SysUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SelfExceptionHandler {

    @ModelAttribute
    public SysUser newUser() {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        return new SysUser();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }

    @ExceptionHandler({SelfException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView processException(HttpServletRequest request, SelfException exception){
        exception.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getErrorMessage());
        modelAndView.setViewName("error_page/error");
        return modelAndView;
    }
}
