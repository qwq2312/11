package springboot.query;

import springboot.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户查询
 *
 * @since 1.0.0 2023-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户查询")
public class YonghuQuery extends Query {
	@Schema(description = "主键")
	private Long id;

	@Schema(description = "创建时间")
	private Date addtime;

	@Schema(description = "用户账号")
	private String yonghuzhanghao;

	@Schema(description = "密码")
	private String mima;

	@Schema(description = "用户姓名")
	private String yonghuxingming;

	@Schema(description = "性别")
	private String xingbie;

	@Schema(description = "联系方式")
	private String lianxifangshi;

	@Schema(description = "头像")
	private String touxiang;

	@Schema(description = "余额")
	private Float money;

}