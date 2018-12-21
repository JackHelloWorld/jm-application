package com.jmsoft.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.utils.AnnotationUtils;
import com.jmsoft.common.utils.BeanTools;
import com.jmsoft.common.utils.PageBean;
import com.jmsoft.common.utils.PageQuery;
import com.jmsoft.common.utils.PageUtils;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.sys.config.DictionaryCode;
import com.jmsoft.sys.service.DictionaryService;
import com.jmsoft.user.entity.LoginUser;
import com.jmsoft.user.entity.LoginUserStatusRecord;
import com.jmsoft.user.mybatis.LoginUserDao;
import com.jmsoft.user.repository.LoginUserRepository;
import com.jmsoft.user.repository.LoginUserStatusRecordRepository;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.service.utils.BaseUserService;
import com.jmsoft.user.vo.AdminUserDbVo;
import com.jmsoft.user.vo.LoginUserVo;

@Service(timeout=60000)
@Transactional(rollbackFor=Exception.class)
public class LoginUserServiceImpl extends BaseUserService implements LoginUserService{

	private static final long serialVersionUID = -49295865581840483L;
	
	@Resource
	LoginUserDao loginUserDao;
	
	@Resource
	LoginUserRepository loginUserRepository;
	
	@Resource
	LoginUserStatusRecordRepository loginUserStatusRecordRepository;
	
	@Reference
	DictionaryService dictionaryService;

	@Override
	public ResponseResult list(LoginUserVo loginUserVo, Integer pageNumber, Integer pageSize) throws Exception {

		AnnotationUtils.paramQuery(loginUserVo);
		
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {
			
			@Override
			public List<Map<String, Object>> query() {
				return loginUserDao.list(loginUserVo);
			}
		}, LoginUserVo.class);
		
		return ResponseResult.SUCCESS("获取用户信息成功", pageBean);
	}

	@Override
	public ResponseResult updateInfo(LoginUserVo loginUserVo) throws Exception {
		
		AnnotationUtils.validateEdit(loginUserVo);
		
		if(loginUserVo.getId() == null) 
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		
		LoginUser loginUser = BeanTools.setPropertiesToBean(loginUserVo, LoginUser.class);
		
		//字典检查
		if(!dictionaryService.checkValueAndParentToken(loginUser.getCertificateType(),DictionaryCode.CERTIFICATE_TYPE)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "证件类型选择错误");
		}
		
		if(!dictionaryService.checkValueAndParentToken(loginUser.getSex(),DictionaryCode.SEX)) {
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "性别选择错误");
		}
		
		LoginUser dbUser = loginUserRepository.findTop1ByIdAndStatusIn(loginUser.getId(),new Integer[]{0,1});
		
		if(dbUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		
		loginUser.setAlipayOpenId(dbUser.getAlipayOpenId());
		loginUser.setStatus(dbUser.getStatus());
		loginUser.setUpdateTime(new Date());
		loginUser.setCreateTime(dbUser.getCreateTime());
		loginUser.setWxOpenId(dbUser.getWxOpenId());
		if(Tools.isEmpty(loginUser.getProfileImg()))
			loginUser.setProfileImg(dbUser.getProfileImg());
		loginUserRepository.save(loginUser);
		return ResponseResult.SUCCESSM("信息编辑成功");
	}

	@Override
	public ResponseResult delete(Long id, AdminUserDbVo user) {
		
		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");
		
		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),2,loginUser.getStatus(),new Date());
		
		loginUserStatusRecordRepository.save(loginUserStatusRecord);
		
		loginUser.setStatus(2);
		loginUser.setDeleteTime(new Date());
		
		loginUserRepository.save(loginUser);
		
		return ResponseResult.SUCCESSM("删除成功");
	}

	@Override
	public ResponseResult success(Long id, AdminUserDbVo user) {
		
		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");
		
		if(loginUser.getStatus() == 0)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前信息正常,无需重复启用");
		
		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),0,loginUser.getStatus(),new Date());
		
		loginUserStatusRecordRepository.save(loginUserStatusRecord);
		
		loginUser.setStatus(0);
		
		loginUserRepository.save(loginUser);
		
		return ResponseResult.SUCCESSM("启用");
	
	}

	@Override
	public ResponseResult block(Long id, AdminUserDbVo user) {
		
		LoginUser loginUser = loginUserRepository.findTop1ByIdAndStatusIn(id, new Integer[]{0,1});
		if(loginUser == null)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在,无法删除");
		
		if(loginUser.getStatus() == 1)
			return ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "当前已停用,无需重复操作");
		
		//生成记录
		LoginUserStatusRecord loginUserStatusRecord = new LoginUserStatusRecord(null,id,user.getId(),1,loginUser.getStatus(),new Date());
		
		loginUserStatusRecordRepository.save(loginUserStatusRecord);
		
		loginUser.setStatus(1);
		
		loginUserRepository.save(loginUser);
		
		return ResponseResult.SUCCESSM("停用成功");
	
	}
	
	

	
}
