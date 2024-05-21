package springboot.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻表
 */
@Data
@Schema(description = "新闻表")
public class ContentInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	@ExcelIgnore
	private Long id;

	private String title;

	private String photoUrl;

	private Integer readcount;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

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

	private String processingTime;

	private Integer afterProcess;


}