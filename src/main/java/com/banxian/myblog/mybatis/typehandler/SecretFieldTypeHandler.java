package com.banxian.myblog.mybatis.typehandler;

import com.banxian.myblog.common.util.AesUtil;
import com.banxian.myblog.mybatis.alias.SecretField;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 加密数据库的敏感信息，如身份证等
 *
 * @author wangpeng
 * @since 2022-8-30 9:30:04
 */
@Slf4j
@MappedTypes(SecretField.class)
public class SecretFieldTypeHandler extends BaseTypeHandler<String> {

    public static String key = "sd3!@#kdlfhs";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (StringUtils.hasText(parameter)) {
                String encryptStr = AesUtil.aesEncrypt(parameter, key);
                ps.setString(i, encryptStr);
            }
        } catch (Exception e) {
            ps.setString(i, parameter);
            log.error("mybatis加密参数异常，i:{},parameter:{}", i, parameter);
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        try {
            if (StringUtils.hasText(columnValue)) {
                columnValue = AesUtil.aesDecrypt(columnValue, key);
            }
        } catch (Exception e) {
            log.error("mybatis解密参数异常，columnName:{}, columnValue:{}", columnName, columnValue);
        }
        return columnValue;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
