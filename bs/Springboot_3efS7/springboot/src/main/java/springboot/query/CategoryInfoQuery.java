package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 新闻分类查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "新闻分类查询")
public class CategoryInfoQuery extends Query {
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Integer paixu;

    @Schema(description = "查询类型")
    private String queryUrl;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

}