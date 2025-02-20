package cn.itbeien.multi.mapper.testtwo;


import cn.itbeien.multi.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
public interface UserTwoMapper {


	@Select("SELECT * FROM sys_user")
	@Results({
			@Result(property = "userSex",  column = "user_sex"),
			@Result(property = "nickName", column = "nick_name")
	})
	List<SysUser> getAll();

	@Select("SELECT * FROM sys_user WHERE id = #{id}")
	@Results({
			@Result(property = "userSex",  column = "user_sex"),
			@Result(property = "nickName", column = "nick_name"),
			@Result(property = "userName", column = "user_name")
	})
	SysUser getOne(Long id);

	@Insert("INSERT INTO sys_user(user_name,password,user_sex) VALUES(#{userName}, #{password}, #{userSex})")
	void insert(SysUser user);

	@Update("UPDATE sys_user SET user_name=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(SysUser user);

	@Delete("DELETE FROM sys_user WHERE id =#{id}")
	void delete(Long id);

}