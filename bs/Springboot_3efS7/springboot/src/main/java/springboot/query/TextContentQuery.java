package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 查询
* @since 1.0.0 2023-11-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "查询")
public class TextContentQuery extends Query {
    @Schema(description = "文本消息ID")
    private Long id;

    @Schema(description = "文本消息")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] createTime;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] updateTime;

}