package cn.itbeien.multi;

import cn.itbeien.multi.entity.SysUser;
import cn.itbeien.multi.mapper.testone.UserOneMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@SpringBootTest
public class MultiBootTest {

    @Autowired
    private UserOneMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new SysUser("itbeien", "123", "0"));
        userMapper.insert(new SysUser("itdatong", "12", "1"));
        userMapper.insert(new SysUser("abc", "123", "1"));

    }

    @Test
    public void testQuery() throws Exception {
        List<SysUser> users = userMapper.getAll();
        if(users==null || users.isEmpty()){
            System.out.println("is null");
        }else{
            System.out.println(users.size());
        }
    }


    @Test
    public void testUpdate() throws Exception {
        SysUser user = userMapper.getOne(1000l);
        System.out.println(user.toString());
        user.setNickName("itbeien");
        userMapper.update(user);
	}
}
