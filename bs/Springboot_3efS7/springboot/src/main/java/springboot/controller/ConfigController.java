package springboot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.Result;
import springboot.convert.ConfigConvert;
import springboot.entity.ConfigEntity;
import springboot.query.ConfigQuery;
import springboot.service.ConfigService;
import springboot.vo.ConfigVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 配置文件
 */
@RestController
@RequestMapping("config")
@Tag(name = "配置文件")
@AllArgsConstructor
public class ConfigController {
	private final ConfigService configService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<ConfigVO>> page(@RequestBody @Valid ConfigQuery query) {
		PageResult<ConfigVO> page = configService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<ConfigVO>> list(@RequestBody @Valid ConfigQuery query) {
		List<ConfigVO> list = configService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<ConfigVO> get(@RequestSingleParam(value = "id") Long id) {
		ConfigEntity entity = configService.getById(id);
		return Result.ok(ConfigConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody ConfigVO vo) {
		configService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid ConfigVO vo) {
		configService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		configService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid ConfigQuery query) {
		configService.export(query);
	}

	@PostMapping("import")
	@Operation(summary = "导入")
	public Result<Boolean> importExcel(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return Result.error("请选择需要上传的文件");
		}
		configService.importExcel(file);
		return Result.ok();
	}
}