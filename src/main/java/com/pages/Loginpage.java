package com.pages;

import com.Baseclass;

import com.utility.*;

public class Loginpage extends Baseclass {

	public void doLogin(String browser) {

		Logutil.info(Constants.URL);

		driver.get(Constants.URL);

	}

}
