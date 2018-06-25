package com.hm.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.hm.exception.FileReaderException;




public class FileReaderHelper {
	
	/** 
	 * Get File from given location & read file content return as list
	 * **/
	public File getFile(String fileNameAndLocation) {
		File file = null;
		try {
			ClassLoader classLoader = new FileReaderHelper().getClass().getClassLoader();
			file = new File(classLoader.getResource(fileNameAndLocation).getFile());
		}catch (NullPointerException np) {
			throw new FileReaderException("File not found in given path "+fileNameAndLocation );
		}
		return file;
	}
	public List<String> readFilecontent(String fileNameAndLocation) {
		List<String> fileContent = new ArrayList<String>();
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(getFile(fileNameAndLocation)));
            String line = br.readLine();
            if(line == null) {
            	throw new FileReaderException("File is empty can't perform sorting operation");
            }
            while (line != null) {
            	fileContent.add(line);
            	line = br.readLine();
            }
        } catch (FileNotFoundException fe) {
        	System.out.println("File not found "+ fe.getMessage());
        } catch (IOException e) {
        	System.out.println("Error while reading file "+e.getMessage());
        } finally {
        	    IOUtils.closeQuietly(br);
        }
        return fileContent;
	}

}
