package springboot.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@TableName("user")
@Data
public class UsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	/**
	 * 用户账号
	 */
	private String username;

	/**
	 * 姓名
	 **/
	private String name;

	/**
	 * 手机
	 **/
	private String phone;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户类型
	 */
	private String role;

	private Date addtime;

	private String touxiang;

	private String email;

	private Double money;
}
