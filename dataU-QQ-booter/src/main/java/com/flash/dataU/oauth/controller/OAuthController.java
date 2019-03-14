package com.flash.dataU.oauth.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author sunyiming (sunyiming170619@credithc.com)
 * @version 0.0.1-SNAPSHOT
 * @since 2017年08月23日 16时13分
 */
@Controller
public class OAuthController {

    private static String getLocalHost() throws UnknownHostException {
        //return InetAddress.getLocalHost().getHostAddress();
        //return "10.100.19.211";
        return "127.0.0.1";
    }

    private static String getRedirect_uri() throws UnknownHostException {
        return "http://"+ getLocalHost() +":7001/index";
    }

    /**
     * （A）用户访问客户端，后者将前者导向认证服务器，即跳转到登录页面
     */
    @RequestMapping("authorize")
    public String authorize(String response_type, String client_id, String redirect_uri, String scope, String state){

        return "login";
    }

    /**
     * （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
     */
    @RequestMapping("login")
    public void login(String username, String password, HttpServletResponse response) throws IOException {
        // 验证用户名密码是否正确
        System.out.println("username-验证：ok"+username);
        System.out.println("password-验证：ok"+password);
        //这里是验证通过之后的重定向
        response.sendRedirect(getRedirect_uri() + "?code=xxx&state=hehe");
    }

    /**
     * （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
     */
    @RequestMapping("getTokenByCode")
    @ResponseBody
    public String getTokenByCode(String grant_type, String code, String client_id, String redirect_uri) throws IOException {
        // 判断client_id、redirect_uri、code是否正确
        // 返回token
        return "thisIsAccessToken";
//        return "{access_token:yyy," +
//            "token_type:bearer," +
//            "expires_in:600," +
//            "refresh_token:zzz}";
    }

    /**
     * （F）资源服务器确认令牌无误，同意向客户端开放资源。。
     */
    @RequestMapping("getUserinfoByToken")
    @ResponseBody
    public String getUserinfoByToken(String token, HttpServletRequest request) throws IOException {
        System.out.println("------->"+request.getQueryString());
        if("access_token=thisIsAccessToken".equals(request.getQueryString())){
            System.out.println("token校验成功，接下来组织返回用户信息");
            return "{userGuid:j3jlk2jj32li43i," +
                    "username:Syao," +
                    "mobile:18818885858}";
        }
        // 判断token是否正确
        System.out.println("token ="+token);
        return "{userGuid:j3jlk2jj32li43i," +
            "username:Tom," +
            "mobile:18811412324}";
    }

}
