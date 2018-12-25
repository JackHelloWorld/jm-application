package com.jmsoft;

import com.jmsoft.generate.address.Config;
import com.jmsoft.generate.address.GenerateInfo;

public class GenerateAddressApplication {

	public static void main(String[] args) throws Exception {
		
		GenerateInfo get = new GenerateInfo();
		get.address1(Config.find_url);

	}

}
