package springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论表
 *
 * @since 1.0.0 2023-11-07
 */
@Data
@Schema(description = "评论表")
public class DiscussVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "子评论")
	private List<DiscussVO> children;

}