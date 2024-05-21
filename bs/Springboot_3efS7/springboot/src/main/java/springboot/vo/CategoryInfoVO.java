package springboot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import springboot.common.utils.DateUtils;
    import java.util.Date;

/**
* 新闻分类
*
*/
@Data
@Schema(description = "新闻分类")
public class CategoryInfoVO implements Serializable {
private static final long serialVersionUID = 1L;

    @ExcelIgnore
    private String id;

    @Schema(description = "名称")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer paixu;

    @Schema(description = "查询类型")
    @ExcelProperty("查询类型")
    private String queryUrl;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @ExcelProperty("创建时间")
    private Date addtime;


}