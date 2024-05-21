package springboot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import springboot.common.utils.DateUtils;
    import java.util.Date;

/**
* 公告信息
*
*/
@Data
@Schema(description = "公告信息")
public class NewsVO implements Serializable {
private static final long serialVersionUID = 1L;

        @Schema(description = "主键")
    private Long id;

        @Schema(description = "创建时间")
        @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date addtime;

        @Schema(description = "标题")
    private String title;

        @Schema(description = "简介")
    private String introduction;

        @Schema(description = "图片")
    private String picture;

        @Schema(description = "内容")
    private String content;


}