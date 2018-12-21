package com.jmsoft.user.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jmsoft.user.vo.LoginUserVo;

@Repository
public interface LoginUserDao {

	List<Map<String, Object>> list(LoginUserVo loginUserVo);
	
}
