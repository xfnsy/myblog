package com.banxian.myblog.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-18
 */
public class Demo implements Serializable {

    private static final long serialVersionUID = 4541838356747121200L;

    /**
     * 主键ID
     */
    @TableId(value = "demo_id", type = IdType.NONE)
    private Integer demoId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    public Integer getDemoId() {
        return demoId;
    }

    public void setDemoId(Integer demoId) {
        this.demoId = demoId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Demo{" +
            "demoId=" + demoId +
            ", name=" + name +
            ", age=" + age +
            ", email=" + email +
        "}";
    }
}
