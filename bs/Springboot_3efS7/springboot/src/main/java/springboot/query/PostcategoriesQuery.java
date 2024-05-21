package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import springboot.common.query.Query;

import java.util.Date;

/**
 * 帖子话题分类查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "帖子话题分类查询")
public class PostcategoriesQuery extends Query {
	@Schema(description = "主键")
	private Long id;

	@Schema(description = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date[] addtime;

	@Schema(description = "创建人")
	private Long userid;

	@Schema(description = "创建人名字")
	private String username;

	@Schema(description = "分类")
	private String categoryName;

}