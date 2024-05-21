package springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子话题分类
 */
@Data
@Schema(description = "帖子话题分类")
public class PostcategoriesVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	private Long id;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date addtime;

	@Schema(description = "创建人")
	private Long userid;

	@Schema(description = "创建人名字")
	private String username;

	@Schema(description = "分类")
	private String categoryName;


}