package com.lh.test;

import com.lh.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: SpringBootJwtTest
 * package: com.lh.test
 * Description:
 *
 * @Author lh
 * @Create 2024/9/27 0:58
 * @Version 1.0
 */
@SpringBootTest
public class SpringBootJwtTest {

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void test(){
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);
        int i = jwtHelper.getUserId(token).intValue();
        System.out.println("i = " + i);
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }
}
