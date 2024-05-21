package springboot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏表
 */
@Data
@Schema(description = "收藏表")
public class StoreupVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	private Long id;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date addtime;

	@Schema(description = "用户id")
	private Long userid;

	@Schema(description = "关联id")
	private Long refid;

	@Schema(description = "表名")
	private String tablename;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "图片")
	private String picture;

	@Schema(description = "类型(-1:踩1:浏览,2:关注,3:收藏,4:点赞,5:喜欢)")
	private Integer type;

	@Schema(description = "推荐类型")
	private String inteltype;

	@Schema(description = "备注")
	private String remark;


}