package com.jmsoft.common.utils;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jmsoft.common.annotation.QueryConfig;
import com.jmsoft.common.annotation.ValidateEdit;
import com.jmsoft.common.annotation.QueryConfig.QueryType;
import com.jmsoft.common.data.ResponseResult;
import com.jmsoft.common.data.ResultCode;
import com.jmsoft.common.data.ResultDic;
import com.jmsoft.common.exception.BizException;
import com.jmsoft.common.exception.NoException;
import com.jmsoft.common.exception.ParamException;

/**
 * 查询参数处理工具
 * @author Jack
 *
 */
public class AnnotationUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationUtils.class);

	/**
	 * 参数处理
	 * @param paramObject
	 */
	public static <T extends Serializable> void paramQuery(T paramObject){

		if(paramObject != null){
			Field[] fields = paramObject.getClass().getDeclaredFields();
			for (Field field : fields) {
				if(field.getAnnotation(QueryConfig.class) == null || field.getAnnotation(QueryConfig.class).value() == QueryType.NONE)
					continue;
				field.setAccessible(true);
				try {
					Object value = field.get(paramObject);
					if(value instanceof String){
						String v = (String) value;
						if(v != null){
							v = v.trim();
							if(v.length() == 0){
								field.set(paramObject, "");
								continue;
							}
							StringBuffer newValue = new StringBuffer("");
							switch (field.getAnnotation(QueryConfig.class).value()) {
							case LIKELEFT:
								newValue.append("%").append(v);
								break;
							case LIKERIGHT:
								newValue.append(v).append("%");
								break;
							case LIKEBOTH:
								newValue.append("%").append(v).append("%");
								break;
							case DATETIMEMAX:
								newValue.append(v).append(" 23:59:59");
								break;
							case DATETIMEMIN:
								newValue.append(v).append(" 00:00:00");
								break;

							default:
								newValue.append(v);
								break;
							}
							field.set(paramObject, newValue.toString());
						}
					}
				}catch (Exception e) {
					LOGGER.error("paramQueryError:", e);
				}
			}
		}
	}

	/**
	 * 属性值校验
	 * @param dto
	 * @throws NoException 
	 * @throws BizException 
	 * @throws BusinessException 校验结果
	 */
	public static <T extends Serializable> void validateEdit(T dto) throws ParamException, NoException, BizException{

		if(dto != null){

			Field[] fields = dto.getClass().getDeclaredFields();
			for (Field field : fields) {
				if(field.getAnnotation(ValidateEdit.class) == null)
					continue;
				field.setAccessible(true);
				ValidateEdit validate = field.getAnnotation(ValidateEdit.class);
				try {
					Object value = field.get(dto);

					if(value == null || Tools.isNull(value))
						ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, validate.message()).throwBizException();

					boolean token = false;

					switch (validate.type()) {
					case INTEGER:
						token = !Tools.isInteger(value);
						break;
					case LONG:
						token = !Tools.isLong(value);
						break;
					case BIGDECIMAL:
						token = !Tools.isBigDecimal(value.toString());
						break;
					case DOUBLE:
						token = !Tools.isDouble(value);
						break;
					case PHONE:
						token = !Tools.checkPhone(value.toString());
						break;

					default:
						break;
					}

					if(token)
						ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, validate.message()).throwParamException();

				}catch (ParamException e) {
					LOGGER.error("validateEditError ParamException:", e);
					throw e;
				}catch (BizException e) {
					LOGGER.error("validateEditError BizException:", e);
					throw e;
				}catch (Exception e) {
					ResponseResult.ERROR(ResultDic.SYS_ERROR).throwNoException();
				}
			}

		}else
			ResponseResult.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在").throwParamException();


	}

}
