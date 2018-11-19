package com.jm.sys.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.jm.sys.data.RequestMap;
import com.jm.sys.data.ResponseResult;
import com.jm.sys.data.ResultCode;
import com.jm.sys.utils.Tools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController implements Serializable{

	private static final long serialVersionUID = -8410373602704441539L;

	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected HttpSession session;
	
	protected RequestMap requestMap;
	
	@ModelAttribute
	public void populateModel(HttpServletRequest request,HttpServletResponse response) { 
		this.response = response;
		this.request = request;
		RequestMap requestMap = new RequestMap();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key =  enumeration.nextElement();
			requestMap.put(key, request.getParameter(key));
		}
		this.requestMap = requestMap;
		this.session = request.getSession();
	} 
	
	/**
	 * 将map数据分别设置到request作用域
	 * @param request
	 * @param result
	 */
	protected void setAttribute(HttpServletRequest request,Map<String, Object> result){
		for (String key : result.keySet()) {
			request.setAttribute(key, result.get(key));
		}
	}
	
	public String getBase64Param(HttpServletRequest request,String paramName){
		String value = request.getParameter(paramName);
		if(Tools.isEmpty(value)) return value;
		try {
			Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("decode", String.class);  
			mainMethod.setAccessible(true);  
			byte[] ret=(byte[])mainMethod.invoke(null, value);
			return new String(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String uploadLocal(MultipartFile img) throws Exception{
		if(img!=null && img.getSize()>0){
			try {
				if(img.getOriginalFilename()==null || img.getOriginalFilename().trim().length()==0)
						ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "文件名称不能为空").throwBizException();
				String newFileName = Tools.MD5(img.getBytes());
				int i = img.getOriginalFilename().trim().lastIndexOf('.');
				if(i!=-1){
					newFileName = newFileName+img.getOriginalFilename().substring(i, img.getOriginalFilename().trim().length());
				}
				String pathDirs = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/static/file/images"); 
				File dirs = new File(pathDirs);
				File file = new File(pathDirs.concat("/").concat(newFileName));
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				if(!file.exists()){
					OutputStream outputStream = new FileOutputStream(file);
					outputStream.write(img.getBytes());
					outputStream.close();
				}
				return newFileName;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return null;
	}


	/**
	 * 获得客户端ip
	 * @param request
	 * @return
	 */
	public String getIpAddress() {
		String ip = request.getHeader("X-Forwarded-For");
		if (log.isInfoEnabled()) {
			log.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (log.isInfoEnabled()) {
					log.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (log.isInfoEnabled()) {
					log.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (log.isInfoEnabled()) {
					log.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (log.isInfoEnabled()) {
					log.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (log.isInfoEnabled()) {
					log.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
}
