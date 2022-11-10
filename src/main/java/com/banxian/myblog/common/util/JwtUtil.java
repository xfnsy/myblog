package com.banxian.myblog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.banxian.myblog.common.base.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author wangpeng
 * @since 2022-1-7 15:32:37
 */
@Slf4j
public class JwtUtil {

    private static final String jwtSecret = "tudoudawang123@1,.sWDsLOI_US";
    private static final String jwtIssuer = "banXian";
    // token持续时间,分钟
    private static final long TOKEN_DURING = 30L;
    private static final long REFRESH_TOKEN_DURING = 3L * 24 * 60;

    private static final Algorithm DEFAULT_ALGORITHM = Algorithm.HMAC256(jwtSecret);

    /**
     * 对称加密算法，HMAC256创建JWT
     */
    public static String createJWT(Object data, Long during) {
        String token = "";
        try {
            token = JWT.create()
                    .withIssuer(jwtIssuer)   //发布者
//                    .withSubject("subject")    //主题
//                    .withAudience("王大")     //观众，相当于接受者
//                    .withJWTId(UUID.randomUUID().toString())    //编号
                    .withIssuedAt(new Date())   // 生成签名的时间
                    .withNotBefore(new Date())  //生效时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + during * 60 * 1000))    // 生成签名过期时间
                    .withClaim("data", JacksonUtil.toJson(data)) //自定义数据
                    .sign(DEFAULT_ALGORITHM);
        } catch (JWTCreationException e) {
            log.error("createJWT error：", e);
        }
        return token;
    }

    public static String createJWT(Object data) {
        return createJWT(data, TOKEN_DURING);
    }

    public static String createRefreshJWT(Object data) {
        return createJWT(data, REFRESH_TOKEN_DURING);
    }

    public static <T> T parseToken(String token, Class<T> clazz) {
        Map<String, Claim> claims = getClaims(token);
        if (claims == null || claims.isEmpty()) {
            return null;
        }
        return JacksonUtil.parse(claims.get("data").asString(), clazz);
    }


    private static Map<String, Claim> getClaims(String token) {
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
    private static DecodedJWT getDecodedJWT(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.require(DEFAULT_ALGORITHM).build().verify(token);
        } catch (AlgorithmMismatchException e) {
            log.error("decodedJWT error：{}", "token算法不一致");
        } catch (InvalidClaimException e) {
            log.error("decodedJWT error：{}", "无效的token声明");
        } catch (JWTDecodeException e) {
            log.error("decodedJWT error：{}", "token解码异常");
        } catch (SignatureVerificationException e) {
            log.error("decodedJWT error：{}", "token签名无效");
        } catch (TokenExpiredException e) {
            log.warn("decodedJWT error：{}", "token已过期");
        } catch (Exception e) {
            log.error("decodedJWT error：{}", "其他异常");
        }
        return decodedJWT;
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("admin");
        userInfo.setUserId(101);
        String token = createJWT(userInfo);
        System.out.println(token);
        System.out.println(parseToken(token, UserInfo.class));

    }
}
