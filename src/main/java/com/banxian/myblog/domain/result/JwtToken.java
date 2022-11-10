package com.banxian.myblog.domain.result;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JwtToken {

    private String token;

    private String refreshToken;

}
