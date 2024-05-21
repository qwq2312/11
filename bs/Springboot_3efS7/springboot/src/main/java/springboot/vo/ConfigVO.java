package springboot.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置文件
 */
@Data
@Schema(description = "配置文件")
public class ConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	@ExcelIgnore
	private Long id;

	@Schema(description = "配置参数名称")
	@ExcelProperty("配置参数名称")
	private String name;

	@Schema(description = "配置参数值")
	@ExcelProperty("配置参数值")
	private String value;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@ExcelProperty("创建时间")
	private Date addtime;


}