package com.notes;

import org.testng.annotations.DataProvider;

public class SOAP_TestData  {
	
	@DataProvider(name="allSOAPrequest")
	public Object[][] allSOAPData(){
		// Two dimensional object
		// 2X2 , 2X3, 3X4
	    return new Object[][] {
	            {"SOAP_Add.xml","AddResult","10"},
	            {"SOAP_Subtract.xml","SubtractResult","0"},
	            {"SOAP_Multiply.xml","MultiplyResult","25"},
	            {"SOAP_Divide.xml","DivideResult","1"}
	            };
	}


}
