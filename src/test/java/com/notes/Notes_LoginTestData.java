package com.notes;

import org.testng.annotations.DataProvider;

public class Notes_LoginTestData  {
	
	@DataProvider(name="create_note")
	public Object[][] createNotes(){
		// Two dimensional object
		// 2X2 , 2X3, 3X4
	    return new Object[][] {
	            {"vishvajit@abc.com","","A valid email address is required"},
	            { "vishvajit@abc", "123abc", "A valid email address is required" },
				{ "", "", "A valid email address is required" },
				{ "vishvajitabc.com", "abc123", "A valid email address is required" },
				{ "anurag@abc", "abc123", "A valid email address is required" },
				{ "vishvajit", "abc123", "A valid email address is required" }
	    };
	}


}
