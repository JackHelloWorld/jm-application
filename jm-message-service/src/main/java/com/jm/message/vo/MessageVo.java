package com.jm.message.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageVo implements Serializable{

	private static final long serialVersionUID = -6045362235182564344L;
	/**主题*/
	private String topic;
	/**消息键*/
	private String key;
	/**消息数据*/
	private Object data;
	
}
