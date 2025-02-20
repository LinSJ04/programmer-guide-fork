package cn.itbeien.multi.entity;

import java.io.Serializable;

public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String password;
	private String userSex;
	private String nickName;

	public SysUser() {
		super();
	}

	public SysUser(String userName, String password, String userSex) {
		super();
		this.password = password;
		this.userName = userName;
		this.userSex = userSex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return password;
	}

	public void setPassWord(String passWord) {
		this.password = passWord;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "userName " + this.userName + ", pasword " + this.password + "sex " + userSex;
	}

}