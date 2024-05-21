package springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0.0 2023-11-16
 */
@Data
public class ImageContentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "图片消息ID")
	private Long id;

	@Schema(description = "图片存储路径")
	private String path;

	@Schema(description = "文件ID")
	private Long fileId;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date updateTime;


}