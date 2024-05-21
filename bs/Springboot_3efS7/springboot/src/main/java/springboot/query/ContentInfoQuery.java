package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import springboot.common.query.Query;

import java.util.Date;

/**
 * 新闻表查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "新闻表查询")
public class ContentInfoQuery extends Query {
	@Schema(description = "主键")
	private Long id;

	private String title;

	private String photoUrl;

	private Integer readcount;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date[] createTime;

	private String source;

	private Integer commentCount;

	private Integer enable;

	private Integer sort;

	private String categoryId;

	private String content;

	private String desinfo;

	private String sourceUrl;

	private Integer likeCount;

	private Integer slider;

	private Long userId;

	private Integer afterProcess;

}