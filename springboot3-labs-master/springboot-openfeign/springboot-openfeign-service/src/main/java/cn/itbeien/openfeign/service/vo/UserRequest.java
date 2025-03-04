package cn.itbeien.openfeign.service.vo;

import lombok.Data;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Data
public class UserRequest {

    private String name;
    private Integer gender;

    public String getName() {
        return name;
    }

    public UserRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public UserRequest setGender(Integer gender) {
        this.gender = gender;
        return this;
    }
}
