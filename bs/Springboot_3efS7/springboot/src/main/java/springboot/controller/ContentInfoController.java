package springboot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.CallPythonUtils;
import springboot.common.utils.GloabUtils;
import springboot.common.utils.Result;
import springboot.convert.ContentInfoConvert;
import springboot.entity.ContentInfoEntity;
import springboot.query.CategoryInfoQuery;
import springboot.query.ContentInfoQuery;
import springboot.service.CategoryInfoService;
import springboot.service.ContentInfoService;
import springboot.service.StoreupService;
import springboot.vo.CategoryInfoVO;
import springboot.vo.ContentInfoVO;
import springboot.vo.StoreupVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 新闻表
 */
@RestController
@RequestMapping("content_info")
@Tag(name = "新闻表")
@AllArgsConstructor
public class ContentInfoController {
	private final ContentInfoService contentInfoService;
	private final CategoryInfoService categoryInfoService;
	private final StoreupService storeupService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<ContentInfoVO>> page(@RequestBody @Valid ContentInfoQuery query) {
		PageResult<ContentInfoVO> page = contentInfoService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	@SaIgnore
	public Result<List<ContentInfoVO>> list(@RequestBody @Valid ContentInfoQuery query) {
		List<ContentInfoVO> list = contentInfoService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<ContentInfoVO> get(@RequestSingleParam(value = "id") Long id) {
		StoreupVO storeupVO = new StoreupVO();
		storeupVO.setUserid(GloabUtils.getUserId());
		storeupVO.setRefid(id);
		storeupVO.setType(1);
		storeupService.save(storeupVO);
		ContentInfoEntity entity = contentInfoService.getById(id);
		entity.setReadcount(entity.getReadcount() == null ? 0 : entity.getReadcount() + 1);
		contentInfoService.updateById(entity);
		return Result.ok(ContentInfoConvert.INSTANCE.convert(entity));
	}

	@ApiOperation("获取推荐新闻")
	@PostMapping(value = "/recommend")
	public Result<List<ContentInfoVO>> recommend() {
		List<ContentInfoVO> list = contentInfoService.recommend(GloabUtils.getUserId());
		return Result.ok(list);
	}

	@ApiOperation("爬取新闻")
	@PostMapping(value = "/autocheck")
	public Result<Boolean> autocheck() {
		CallPythonUtils.callPython();
		return Result.ok();
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody ContentInfoVO vo) {
		contentInfoService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid ContentInfoVO vo) {
		contentInfoService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		contentInfoService.delete(idList);
		return Result.ok();
	}


	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid ContentInfoQuery query) {
		contentInfoService.export(query);
	}

	/**
	 * （按值统计）
	 */
	@RequestMapping("/value/{xColumnName}/{yColumnName}")
	public Result value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		LambdaQueryWrapper<ContentInfoEntity> wrapper = Wrappers.lambdaQuery();
		List<String> status = new ArrayList<>();
		List<CategoryInfoVO> categoryInfoVOS = categoryInfoService.queryList(new CategoryInfoQuery());
		for (CategoryInfoVO categoryInfoVO : categoryInfoVOS) {
			status.add(categoryInfoVO.getName());
		}
		wrapper.in(ContentInfoEntity::getCategoryId, status);
		List<Map<String, Object>> result = contentInfoService.selectValue(params, wrapper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> m : result) {
			for (String k : m.keySet()) {
				if (m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date) m.get(k)));
				}
			}
		}
		return Result.ok(result);
	}

	/**
	 * （按值统计）时间统计类型
	 */
	@RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
	public Result valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		params.put("timeStatType", timeStatType);
		LambdaQueryWrapper<ContentInfoEntity> wrapper = Wrappers.lambdaQuery();
		List<String> status = new ArrayList<>();
		List<CategoryInfoVO> categoryInfoVOS = categoryInfoService.queryList(new CategoryInfoQuery());
		for (CategoryInfoVO categoryInfoVO : categoryInfoVOS) {
			status.add(categoryInfoVO.getName());
		}
		wrapper.in(ContentInfoEntity::getCategoryId, status);
		List<Map<String, Object>> result = contentInfoService.selectTimeStatValue(params, wrapper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> m : result) {
			for (String k : m.keySet()) {
				if (m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date) m.get(k)));
				}
			}
		}
		return Result.ok(result);
	}

	/**
	 * 分组统计
	 */
	@RequestMapping("/group/{columnName}")
	public Result group(@PathVariable("columnName") String columnName, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		params.put("column", columnName);
		LambdaQueryWrapper<ContentInfoEntity> wrapper = Wrappers.lambdaQuery();
		List<String> status = new ArrayList<>();
		List<CategoryInfoVO> categoryInfoVOS = categoryInfoService.queryList(new CategoryInfoQuery());
		for (CategoryInfoVO categoryInfoVO : categoryInfoVOS) {
			status.add(categoryInfoVO.getName());
		}
		wrapper.in(ContentInfoEntity::getCategoryId, status);
		List<Map<String, Object>> result = contentInfoService.selectGroup(params, wrapper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> m : result) {
			for (String k : m.keySet()) {
				if (m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date) m.get(k)));
				}
			}
		}
		return Result.ok(result);
	}
}