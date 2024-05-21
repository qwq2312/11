package springboot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.PwdUtil;
import springboot.common.utils.Result;
import springboot.common.utils.TokenEntity;
import springboot.constants.Constants;
import springboot.convert.AdminConvert;
import springboot.entity.AdminEntity;
import springboot.query.AdminQuery;
import springboot.service.AdminService;
import springboot.vo.AdminVO;
import springboot.vo.SysAccountLoginVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理用户表
 */
@RestController
@RequestMapping("admin")
@Tag(name = "后台管理用户表")
@AllArgsConstructor
public class AdminController {
	private final AdminService adminService;
	private static final String StpPre = "admin";

	/**
	 * 账号密码登录
	 */
	@SaIgnore
	@PostMapping(value = "/login")
	public Result<TokenEntity> login(@RequestBody SysAccountLoginVO sysAccountLoginVO) {
		AdminEntity user = adminService.getByUsername(sysAccountLoginVO.getUsername());
		if (user == null || !user.getPassword().equals(PwdUtil.encrypt(sysAccountLoginVO.getPassword()))) {
			return Result.error("账号或密码不正确");
		}
		StpUtil.login(StpPre + user.getId());
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUsername(user.getUsername());
		tokenEntity.setUserid(user.getId());
		tokenEntity.setRole(user.getRole());
		tokenEntity.setTablename("admin");
		tokenEntity.setAddtime(user.getAddtime());
		tokenEntity.setAvatarUrl(user.getAvatarurl());
		tokenEntity.setToken(StpUtil.getTokenValue());
		StpUtil.getSession().set(Constants.CURRENT_USER, tokenEntity);
		return Result.ok(tokenEntity);
	}

	/**
	 * 注册
	 */
	@SaIgnore
	@PostMapping(value = "/register")
	public Result<Boolean> register(@RequestBody AdminEntity user) {
		if (adminService.getOne(new QueryWrapper<AdminEntity>().eq("username", user.getUsername())) != null) {
			return Result.error("用户已存在");
		}
		user.setPassword(PwdUtil.encrypt(user.getPassword()));
		adminService.save(user);
		return Result.ok(true);
	}

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<AdminVO>> page(@RequestBody @Valid AdminQuery query) {
		PageResult<AdminVO> page = adminService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	public Result<List<AdminVO>> list(@RequestBody @Valid AdminQuery query) {
		List<AdminVO> list = adminService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<AdminVO> get(@RequestSingleParam(value = "id") Long id) {
		AdminEntity entity = adminService.getById(id);
		return Result.ok(AdminConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody AdminVO vo) {
		adminService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid AdminVO vo) {
		if (StrUtil.isNotBlank(vo.getPassword())) {
			vo.setPassword(PwdUtil.encrypt(vo.getPassword()));
		}
		adminService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		adminService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid AdminQuery query) {
		adminService.export(query);
	}

	@PostMapping("importAdmin")
	@Operation(summary = "导入")
	public Result<Boolean> importAdmin(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return Result.error("请选择需要上传的文件");
		}
		adminService.importAdmin(file, PwdUtil.encrypt(Constants.RESETPASS));
		return Result.ok();
	}
}