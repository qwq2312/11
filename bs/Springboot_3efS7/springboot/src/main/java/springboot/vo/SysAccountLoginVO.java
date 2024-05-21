package springboot.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 账号登录
 */
@Data
public class SysAccountLoginVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String key;

	private String captcha;

	private String touxiang;
}