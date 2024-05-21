package springboot.query;

import springboot.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 手机信息评论表查询
 *
 * @since 1.0.0 2023-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "手机信息评论表查询")
public class DiscussshoujixinxiQuery extends Query {
	@Schema(description = "主键")
	private Long id;

	@Schema(description = "创建时间")
	private Date addtime;

	@Schema(description = "关联表id")
	private Long refid;

	@Schema(description = "用户id")
	private Long userid;

	@Schema(description = "头像")
	private String avatarurl;

	@Schema(description = "用户名")
	private String nickname;

	@Schema(description = "评论内容")
	private String content;

	@Schema(description = "回复内容")
	private String reply;

}