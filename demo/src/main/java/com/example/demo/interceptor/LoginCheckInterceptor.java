package com.example.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.utils.JwtUtils;


@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    //目标方法资源运行之前执行,true为放行,false为不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("preHandle运行了");
        //获取请求的url
        String url = request.getRequestURI().toString();

        //判断url中是否包含login(config文件中已经实现了这一点,其实可以删掉)
        if (url.contains("login")) {
            //登录操作直接放行
            return true;
        }

        //获取令牌(token)
        String jwt = request.getHeader("jwtToken");

        //判断令牌是否存在,如果不存在,返回错误结果(未登录)
        if (!StringUtils.hasLength(jwt)) {
            //log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error(Constants.CODE_401,"NOT_LOGIN");
            //手动将对象转换为json
            String notLogin = JSONObject.toJSONString(error);
            //返回错误信息
            response.getWriter().write(notLogin);
            return false;
        }
        //如果jwt存在,则校验jwt
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//jwt令牌解析失败
            e.printStackTrace();
            //log.info("解析令牌失败返回未登录的错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动将对象转换为json
            String notLogin = JSONObject.toJSONString(error);
            //返回错误信息
            response.getWriter().write(notLogin);
            return false;
        }

        //令牌解析成功
        //放行资源(重要的一部)
        return true;
    }

    //目标资源方法运行后运行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle运行了");
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //视图渲染完毕后运行.最后运行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion运行了");
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}
