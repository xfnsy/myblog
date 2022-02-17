package com.banxian.myblog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.banxian.myblog.common.base.UserInfo;

import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author wangpeng
 * @since 2022-1-7 15:32:37
 */
//@Slf4j
public class JwtUtil {

    private static final String jwtSecret = "tudoudawang123@1,.sWDsLOI_US";
    private static final String jwtIssuer = "banXian";
    // token持续时间,分钟
    private static final long DURING = 30L;

    private static final Algorithm DEFAULT_ALGORITHM = Algorithm.HMAC256(jwtSecret);

    /**
     * 对称加密算法，HMAC256创建JWT
     */
    public static String createJWT(Object data) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer(jwtIssuer)   //发布者
//                    .withSubject("myblog")    //主题
//                    .withAudience("王大")     //观众，相当于接受者
//                    .withJWTId(UUID.randomUUID().toString())    //编号
                    .withIssuedAt(new Date())   // 生成签名的时间
                    .withNotBefore(new Date())  //生效时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + DURING * 60 * 1000))    // 生成签名过期时间
                    .withClaim("data", JacksonUtil.toJson(data)) //自定义数据
                    .sign(DEFAULT_ALGORITHM);
        } catch (JWTCreationException e) {
//            log.error("createJWT error：", e);
        }
        return token;
    }

    public static <T> T getData(String token, Class<T> clazz) {
        Map<String, Claim> claims = getClaims(token);
        if (claims == null || claims.isEmpty()) {
            return null;
        }
        return JacksonUtil.parse(claims.get("data").asString(), clazz);
    }


    public static Map<String, Claim> getClaims(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if (decodedJWT == null) {
            return null;
        }
        return decodedJWT.getClaims();
    }


    /**
     * 获取DecodedJWT
     *
     * @param token token令牌
     * @return DecodedJWT
     */
    public static DecodedJWT getDecodedJWT(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.require(DEFAULT_ALGORITHM).build().verify(token);
        } catch (AlgorithmMismatchException e) {
            System.out.println("token算法不一致");
        } catch (InvalidClaimException e) {
            System.out.println("无效的token声明");
        } catch (JWTDecodeException e) {
            System.out.println("token解码异常");
        } catch (SignatureVerificationException e) {
            System.out.println("token签名无效");
        } catch (TokenExpiredException e) {
            System.out.println("token已过期");
        } catch (Exception e) {
            System.out.println("其他异常");
        }
        return decodedJWT;
    }

    public static void main(String[] args) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("admin");
        userInfo.setUserId(101);
        String token = createJWT(userInfo);
        System.out.println(token);
        System.out.println(getData(token,UserInfo.class));

    }
}
