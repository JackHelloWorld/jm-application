package com.jm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.jm.sys.data.SerialNumberNoConfig;
import com.jm.sys.entity.Dictionary;
import com.jm.sys.entity.SerialNumber;
import com.jm.sys.repository.SerialNumberRepository;
import com.jm.sys.service.BaseService;
import com.jm.sys.utils.DatePattern;
import com.jm.sys.utils.Tools;

@Service
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl implements BaseService{

	private static final Character[] DIC = new Character[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};

	@Resource
	SerialNumberRepository serialNumberRepository;
	
	public static final ThreadLocal<Random> random = new ThreadLocal<Random>(){
		protected Random initialValue() {
			return new Random();
		};
	};

	public String generateEncry(int len){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			result.append(DIC[random.get().nextInt(26)]);
		}
		return result.toString();
	}

	public String generateNo(SerialNumberNoConfig config) {
		StringBuffer result = new StringBuffer(config.getPrefix());
		result.append(Tools.getDateToStr(DatePattern.YYYYMMDDD));
		serialNumberRepository.save(new SerialNumber(config.getColumnName()));
		Long count = serialNumberRepository.countByColumnName(config.getColumnName());
		result.append(String.format("%06d", count));
		return result.toString();
	}
	
	
	public List<Dictionary> initDictionary(List<Dictionary> dictionaries,String parentToken){
		List<Dictionary> nodes = new ArrayList<>();
		for (Dictionary dictionary : dictionaries) {
			if(dictionary.getParentToken().equals(parentToken)){
				nodes.add(dictionary);
				List<Dictionary> myNodes = initDictionary(dictionaries,dictionary.getToken());
				dictionary.setNodes(myNodes);
			}
		}
		return nodes;
	}
	
}
