package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 帖子详情查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "帖子详情查询")
public class PostsQuery extends Query {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "话题分类")
    private String categoryId;

    @Schema(description = "发帖地址")
    private String postingAddress;

    @Schema(description = "封面")
    private String picture;

    @Schema(description = "帖子标题")
    private String postTitle;

    @Schema(description = "内容")
    private String postContent;

    @Schema(description = "是否发布(Y/N)")
    private String isPublished;

    @Schema(description = "是否审核通过(Y/N)")
    private String isApproved;

    @Schema(description = "发帖人")
    private String userid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

    @Schema(description = "发帖人名称")
    private String username;

}