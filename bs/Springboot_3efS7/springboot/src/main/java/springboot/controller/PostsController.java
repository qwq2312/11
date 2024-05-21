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
import springboot.convert.PostsConvert;
import springboot.entity.PostsEntity;
import springboot.query.PostsQuery;
import springboot.service.PostsService;
import springboot.vo.PostsVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 帖子详情
 */
@RestController
@RequestMapping("posts")
@Tag(name = "帖子详情")
@AllArgsConstructor
public class PostsController {
	private final PostsService postsService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<PostsVO>> page(@RequestBody @Valid PostsQuery query) {
		PageResult<PostsVO> page = postsService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<PostsVO>> list(@RequestBody @Valid PostsQuery query) {
		List<PostsVO> list = postsService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	@SaIgnore
	public Result<PostsVO> get(@RequestSingleParam(value = "id") Long id) {
		PostsEntity entity = postsService.getById(id);
		return Result.ok(PostsConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody PostsVO vo) {
		postsService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid PostsVO vo) {
		postsService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		postsService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid PostsQuery query) {
		postsService.export(query);
	}
}