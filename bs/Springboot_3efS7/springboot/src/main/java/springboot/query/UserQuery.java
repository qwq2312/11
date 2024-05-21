package springboot.query;

import springboot.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户表查询
 *
 * @since 1.0.0 2023-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户表查询")
public class UserQuery extends Query {
	@Schema(description = "主键")
	private Long id;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "角色")
	private String role;

	@Schema(description = "新增时间")
	private Date addtime;

}