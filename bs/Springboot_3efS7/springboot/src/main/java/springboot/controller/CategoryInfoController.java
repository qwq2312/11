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
import springboot.convert.CategoryInfoConvert;
import springboot.entity.CategoryInfoEntity;
import springboot.query.CategoryInfoQuery;
import springboot.service.CategoryInfoService;
import springboot.vo.CategoryInfoVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 新闻分类
 */
@RestController
@RequestMapping("category_info")
@Tag(name = "新闻分类")
@AllArgsConstructor
public class CategoryInfoController {
	private final CategoryInfoService categoryInfoService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<CategoryInfoVO>> page(@RequestBody @Valid CategoryInfoQuery query) {
		PageResult<CategoryInfoVO> page = categoryInfoService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<CategoryInfoVO>> list(@RequestBody @Valid CategoryInfoQuery query) {
		List<CategoryInfoVO> list = categoryInfoService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<CategoryInfoVO> get(@RequestSingleParam(value = "id") Long id) {
		CategoryInfoEntity entity = categoryInfoService.getById(id);
		return Result.ok(CategoryInfoConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody CategoryInfoVO vo) {
		categoryInfoService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid CategoryInfoVO vo) {
		categoryInfoService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		categoryInfoService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid CategoryInfoQuery query) {
		categoryInfoService.export(query);
	}
}