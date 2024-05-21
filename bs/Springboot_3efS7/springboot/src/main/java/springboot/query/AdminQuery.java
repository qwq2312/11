package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 后台管理用户表查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "后台管理用户表查询")
public class AdminQuery extends Query {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用户姓名")
    private String name;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "联系方式")
    private String phone;

    @Schema(description = "头像")
    private String avatarurl;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "权限")
    private String role;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

}