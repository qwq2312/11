package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 公告信息查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "公告信息查询")
public class NewsQuery extends Query {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "简介")
    private String introduction;

    @Schema(description = "图片")
    private String picture;

    @Schema(description = "内容")
    private String content;

}