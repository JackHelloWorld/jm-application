package com.jmsoft.wxservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.utils.HttpClientUtils;
import com.jmsoft.wxservice.entity.AccessToken;
import com.jmsoft.wxservice.repository.AccessTokenRepository;
import com.jmsoft.wxservice.utils.WeChatConfig;


@Service
@Transactional(rollbackFor=Exception.class)
public class AccessTokenService {
	
	@Resource
	AccessTokenRepository accessTokenRepository;
	
	@Resource
	WeChatConfig weChatConfig;

	public static final ThreadLocal<AccessToken> accessToken = new ThreadLocal<AccessToken>();

	public AccessToken gainAccessToken()  throws BizException {
		
		if ((accessToken.get() == null) || (((AccessToken)accessToken.get()).staleDated())){
			
			AccessToken accessToken1 = accessTokenRepository.findTop1ByExpiresInNotNullOrderByCreateTimeDesc();
			if ((accessToken1 != null) && (!accessToken1.staleDated())){
				accessToken.set(accessToken1);
			}else{
				Map<String, String> param = new HashMap<String,String>();
				param.put("grant_type", "client_credential");
				param.put("appid", weChatConfig.appid);
				param.put("secret", weChatConfig.appsecret);
				String result = HttpClientUtils.doGet("https://api.weixin.qq.com/cgi-bin/token", param);
				JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(result);
				if ((jsonObject.containsKey("access_token")) && (jsonObject.containsKey("expires_in"))){
					AccessToken token = new AccessToken();
					token.setAccessToken(jsonObject.get("access_token").toString());
					token.setExpiresIn(Long.valueOf(((Number)jsonObject.get("expires_in")).longValue()));
					token.setCreateTime(new Date());
					accessTokenRepository.save(token);
					accessToken.set(token);
				}
				else{
					ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "网络错误:"+jsonObject.toString()).throwBizException();
				}
			}
		}
		return (AccessToken)accessToken.get();
	}
}
