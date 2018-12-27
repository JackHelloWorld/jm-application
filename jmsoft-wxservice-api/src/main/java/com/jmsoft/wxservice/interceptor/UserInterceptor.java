package com.jmsoft.wxservice.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jmsoft.common.annotation.ValidateIgnoreLogin;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.common.utils.HttpClientUtils;
import com.jmsoft.common.utils.Tools;
import com.jmsoft.user.service.LoginUserService;
import com.jmsoft.user.vo.LoginUserVo;
import com.jmsoft.wxservice.utils.ThreadData;
import com.jmsoft.wxservice.utils.WeChatConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInterceptor implements HandlerInterceptor{

	@Resource
	WeChatConfig weChatConfig;

	@Reference
	LoginUserService loginUserService;

	/**
	 * 最后执行！！！
	 */
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception exception)	throws Exception {

	}

	/**
	 * Action执行之后，生成视图之前执行！！
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {


		if(handler instanceof HandlerMethod){

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			ValidateIgnoreLogin validateIgnoreLogin = method.getAnnotation(ValidateIgnoreLogin.class);
			if(validateIgnoreLogin != null)
				return true;

			String code = request.getHeader("code");
			if(Tools.notEmpty(code)){

				log.debug("code ==> {}", code);
				//获取用户登录信息
				Map<String, String> param = new HashMap<>();
				param.put("appid", weChatConfig.appid);
				param.put("secret", weChatConfig.appsecret);
				param.put("js_code", code);
				param.put("grant_type", "authorization_code");
				String result = HttpClientUtils.doGet(weChatConfig.code2SessionUrl,	param, "utf-8");

				if(Tools.isEmpty(result)) 
					ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();

				JSONObject object = JSON.parseObject(result);
				Object reCode = object.get("errcode");
				if(reCode == null) 
					ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();

				switch (reCode.toString()) {
				case "-1":
					ResponseResult.ERROR(ResultDic.FREQUENCY).throwBizException();
					break;

				case "40029":
					ResponseResult.DIY_ERROR(ResultCode.ReqErrorCode,"请求无效").throwBizException();
					break;

				case "45011":
					ResponseResult.ERROR(ResultDic.FREQUENCY).throwBizException();
					break;

				default:
					if(!reCode.toString().equals("0")) 
						ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();
					break;
				}

				String openid = object.getString("openid");
				if(Tools.isEmpty(openid))
					ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();

				LoginUserVo loginUserVo = loginUserService.initLoginUserByWeChatOpenId(openid.trim());

				if(loginUserVo == null)
					ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();

				ThreadData.LOGIN_USER.set(loginUserVo);

			}else{
				ResponseResult.ERROR(ResultDic.LOGIN_TIME_OUT).throwBizException();
			}

		}

		return true;

	}

}
