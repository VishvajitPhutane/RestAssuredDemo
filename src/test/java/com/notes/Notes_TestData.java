package com.notes;

import org.testng.annotations.DataProvider;

public class Notes_TestData  {
	
	@DataProvider(name="create_note")
	public Object[][] createNotes(){
		// Two dimensional object
		// 2X2 , 2X3, 3X4
	    return new Object[][] {
	            {"Home","Home_API","Done for Home"},
	            {"Work","Work_API","Done for Work"},
	            {"Personal","Personal_API","Done for Personal"}
	            };
	}


}
