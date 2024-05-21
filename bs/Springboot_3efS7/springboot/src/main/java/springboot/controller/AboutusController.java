package springboot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.Result;
import springboot.convert.AboutusConvert;
import springboot.entity.AboutusEntity;
import springboot.query.AboutusQuery;
import springboot.service.AboutusService;
import springboot.vo.AboutusVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 关于我们
 */
@RestController
@RequestMapping("aboutus")
@Tag(name = "关于我们")
@AllArgsConstructor
public class AboutusController {
	private final AboutusService aboutusService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<AboutusVO>> page(@RequestBody @Valid AboutusQuery query) {
		PageResult<AboutusVO> page = aboutusService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<AboutusVO>> list(@RequestBody @Valid AboutusQuery query) {
		List<AboutusVO> list = aboutusService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<AboutusVO> get(@RequestSingleParam(value = "id") Long id) {
		AboutusEntity entity = aboutusService.getById(id);
		return Result.ok(AboutusConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody AboutusVO vo) {
		aboutusService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid AboutusVO vo) {
		aboutusService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		aboutusService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid AboutusQuery query) {
		aboutusService.export(query);
	}
}