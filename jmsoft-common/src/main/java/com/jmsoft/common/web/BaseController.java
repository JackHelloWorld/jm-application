package com.jmsoft.common.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.jmsoft.common.data.RequestMap;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.utils.Tools;

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
	
}
