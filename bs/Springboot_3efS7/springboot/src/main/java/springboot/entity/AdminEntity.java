package springboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 后台管理用户表
 */

@Data
@TableName("admin")
public class AdminEntity {
	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 用户账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 头像
	 */
	private String avatarurl;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 权限
	 */
	private String role;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;

}