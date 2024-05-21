package springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.common.impl.BaseServiceImpl;
import springboot.common.page.PageResult;
import springboot.common.utils.DateUtils;
import springboot.common.utils.ExcelUtils;
import springboot.common.utils.GloabUtils;
import springboot.convert.PostsConvert;
import springboot.dao.PostsDao;
import springboot.entity.PostsEntity;
import springboot.query.PostsQuery;
import springboot.service.PostsService;
import springboot.vo.PostsVO;

import java.util.Date;
import java.util.List;

/**
 * 帖子详情
 */
@Service
@AllArgsConstructor
public class PostsServiceImpl extends BaseServiceImpl<PostsDao, PostsEntity> implements PostsService {

	@Override
	public PageResult<PostsVO> page(PostsQuery query) {
		IPage<PostsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(PostsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<PostsVO> queryList(PostsQuery query) {
		return PostsConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<PostsEntity> getWrapper(PostsQuery query) {
		LambdaQueryWrapper<PostsEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), PostsEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getCategoryId()), PostsEntity::getCategoryId, query.getCategoryId());
		wrapper.eq(StringUtils.isNotEmpty(query.getPostingAddress()), PostsEntity::getPostingAddress, query.getPostingAddress());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture()), PostsEntity::getPicture, query.getPicture());
		wrapper.eq(StringUtils.isNotEmpty(query.getPostTitle()), PostsEntity::getPostTitle, query.getPostTitle());
		wrapper.eq(StringUtils.isNotEmpty(query.getPostContent()), PostsEntity::getPostContent, query.getPostContent());
		wrapper.eq(StringUtils.isNotEmpty(query.getIsPublished()), PostsEntity::getIsPublished, query.getIsPublished());
		wrapper.eq(StringUtils.isNotEmpty(query.getIsApproved()), PostsEntity::getIsApproved, query.getIsApproved());
		wrapper.eq(StringUtils.isNotEmpty(query.getUserid()), PostsEntity::getUserid, query.getUserid());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), PostsEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.eq(StringUtils.isNotEmpty(query.getUsername()), PostsEntity::getUsername, query.getUsername());
		return wrapper;
	}

	@Override
	public void save(PostsVO vo) {
		if (GloabUtils.isAdmin()) {
			vo.setIsApproved("Y");
		} else {
			vo.setIsApproved("N");
		}
		PostsEntity entity = PostsConvert.INSTANCE.convert(vo);
		entity.setUserid(GloabUtils.getUserId().toString());
		entity.setUsername(GloabUtils.getUserInfo().getUsername());
		baseMapper.insert(entity);
	}

	@Override
	public void update(PostsVO vo) {
		PostsEntity entity = PostsConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(PostsQuery query) {
		List<PostsVO> list = PostsConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(PostsVO.class, "帖子详情" + DateUtils.format(new Date()), null, list);
	}
}