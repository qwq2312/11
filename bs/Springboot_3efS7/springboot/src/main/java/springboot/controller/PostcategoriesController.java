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
import springboot.convert.PostcategoriesConvert;
import springboot.entity.PostcategoriesEntity;
import springboot.query.PostcategoriesQuery;
import springboot.service.PostcategoriesService;
import springboot.vo.PostcategoriesVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 帖子话题分类
 */
@RestController
@RequestMapping("postcategories")
@Tag(name = "帖子话题分类")
@AllArgsConstructor
public class PostcategoriesController {
	private final PostcategoriesService postcategoriesService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<PostcategoriesVO>> page(@RequestBody @Valid PostcategoriesQuery query) {
		PageResult<PostcategoriesVO> page = postcategoriesService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<PostcategoriesVO>> list(@RequestBody @Valid PostcategoriesQuery query) {
		List<PostcategoriesVO> list = postcategoriesService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<PostcategoriesVO> get(@RequestSingleParam(value = "id") Long id) {
		PostcategoriesEntity entity = postcategoriesService.getById(id);
		return Result.ok(PostcategoriesConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody PostcategoriesVO vo) {
		postcategoriesService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid PostcategoriesVO vo) {
		postcategoriesService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		postcategoriesService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid PostcategoriesQuery query) {
		postcategoriesService.export(query);
	}
}