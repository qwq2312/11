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
import springboot.convert.NewsConvert;
import springboot.entity.NewsEntity;
import springboot.query.NewsQuery;
import springboot.service.NewsService;
import springboot.vo.NewsVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 公告信息
 */
@RestController
@RequestMapping("news")
@Tag(name = "公告信息")
@AllArgsConstructor
public class NewsController {
	private final NewsService newsService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<NewsVO>> page(@RequestBody @Valid NewsQuery query) {
		PageResult<NewsVO> page = newsService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<NewsVO>> list(@RequestBody @Valid NewsQuery query) {
		List<NewsVO> list = newsService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	@SaIgnore
	public Result<NewsVO> get(@RequestSingleParam(value = "id") Long id) {
		NewsEntity entity = newsService.getById(id);
		return Result.ok(NewsConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody NewsVO vo) {
		newsService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid NewsVO vo) {
		newsService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		newsService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid NewsQuery query) {
		newsService.export(query);
	}
}