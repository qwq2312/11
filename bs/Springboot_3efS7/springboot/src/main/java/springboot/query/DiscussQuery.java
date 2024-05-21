package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;

import java.util.Date;

/**
 * 评论表查询
 *
 * @since 1.0.0 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "评论表查询")
public class DiscussQuery extends Query {
	private Long id;

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

	@Schema(description = "父id")
	private Long parentId;

	@Schema(description = "点赞数量")
	private Integer likesCount;

	@Schema(description = "创建时间")
	private Date createTime;

}