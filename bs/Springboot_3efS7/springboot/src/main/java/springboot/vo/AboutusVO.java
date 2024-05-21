package springboot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import springboot.common.utils.DateUtils;
    import java.util.Date;

/**
* 关于我们
*
*/
@Data
@Schema(description = "关于我们")
public class AboutusVO implements Serializable {
private static final long serialVersionUID = 1L;

        @Schema(description = "主键")
    private Long id;

        @Schema(description = "创建时间")
        @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date addtime;

        @Schema(description = "标题")
    private String title;

        @Schema(description = "副标题")
    private String subtitle;

        @Schema(description = "内容")
    private String content;

        @Schema(description = "图片1")
    private String picture1;

        @Schema(description = "图片2")
    private String picture2;

        @Schema(description = "图片3")
    private String picture3;


}