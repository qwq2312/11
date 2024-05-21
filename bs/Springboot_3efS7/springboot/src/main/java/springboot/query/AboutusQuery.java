package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 关于我们查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "关于我们查询")
public class AboutusQuery extends Query {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

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