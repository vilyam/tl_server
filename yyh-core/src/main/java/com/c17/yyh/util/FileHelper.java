package com.c17.yyh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

public class FileHelper {
	public static File[] fileFinder( String dirName, final String extension) throws FileNotFoundException{
	    File dir = new File(dirName);
	   
	    if (!dir.exists())
	    	throw new FileNotFoundException();
	    
	    return dir.listFiles(new FilenameFilter() { 
	    	         public boolean accept(File dir, String filename)
	    	              { return filename.endsWith(extension); }
	    	} );
	 }

	public static File[] fileFinder( String dirName, final String mask, boolean exactMatch){
	    File dir = new File(dirName);
	    
	    if (!exactMatch) {
	    	
		    return dir.listFiles(new FilenameFilter() { 
	   	         public boolean accept(File dir, String filename)
	   	              { return filename.toLowerCase().contains(mask.toLowerCase()); }
			    });
	    }
	    else {
	    	
		    return dir.listFiles(new FilenameFilter() { 
   	         public boolean accept(File dir, String filename)
   	              { return filename.equalsIgnoreCase(mask); }
		    });
		} 
		    
	}
}
