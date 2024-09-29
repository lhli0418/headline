package com.lh.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lh.utils.JwtHelper;
import com.lh.utils.Result;
import com.lh.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: LoginProtectInterceptor
 * package: com.lh.interceptor
 * Description:
 *
 * @Author lh
 * @Create 2024/9/28 22:21
 * @Version 1.0
 */
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {
            Result<Object> result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().print(json);
            return false;
        }

        return true;

    }
}
