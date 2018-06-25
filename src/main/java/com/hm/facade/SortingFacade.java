package com.hm.facade;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hm.exception.FileReaderException;
import com.hm.helper.FileReaderHelper;

public class SortingFacade {
	
	FileReaderHelper fileReaderHelper = new FileReaderHelper();
	
	public void performFileSorting(String[] args) {
		
		if(args == null  || args.length < 4) {
			throw new IllegalArgumentException("Missing input argument");
		}
		displaySortedFileContent(sortFileDataAsAnotherFileData(args[0], args[1], 
				extraSpecificColumnDataFromFile(args[2], args[3])));
	}
	
	private void displaySortedFileContent(List<String> sortedFileContents){
		for(String fileContent : sortedFileContents) {
			System.out.println(fileContent);
		}
	}
	/** 
	 * create map where key selected column data value index
	 * which will increase for ever record
	 * **/
	private Map<Integer, Integer> extraSpecificColumnDataFromFile(String fileNameAndLocation, String readColumnType) {
		List<String> fileContents = fileReaderHelper.readFilecontent(fileNameAndLocation);
		Map<Integer, Integer> sortedColumnMap = new HashMap<Integer, Integer>();
		if(!fileContents.isEmpty()) {
			String headerColumnStr = fileContents.remove(0);
			int readColumnTypePosition = getSortColumnPositionOfHeader(headerColumnStr, readColumnType);
			Integer index = 0;
			for(String lineContent : fileContents ) {
				String[] filecolumns = lineContent.split(",");
				sortedColumnMap.put(Integer.valueOf(filecolumns[readColumnTypePosition]), index++);
			}
		}
		return sortedColumnMap;
	}
	
	/** 
	 * sort file1 with file2 content
	 * **/
	private List<String> sortFileDataAsAnotherFileData(String fileNameAndLocation, String readColumnType, final Map<Integer, Integer> sortedColumnMap ) {
		List<String> fileContentsToBeSort = fileReaderHelper.readFilecontent(fileNameAndLocation);
		String headerColumnStr = fileContentsToBeSort.remove(0);
		final int columnTypeToBeSortPosition = getSortColumnPositionOfHeader(headerColumnStr, readColumnType);
		 Collections.sort(fileContentsToBeSort, new Comparator<String>() {
			public int compare(String leftStr, String rightStr) {
				Integer leftIndex = getIndexFromString(leftStr, columnTypeToBeSortPosition, sortedColumnMap);
				Integer rightIndex = getIndexFromString(rightStr, columnTypeToBeSortPosition, sortedColumnMap);
				if (leftIndex == null) {
	                return 1;
	            }
	            if (rightIndex == null) {
	                return -1;
	            }
				return Integer.compare(leftIndex, rightIndex);
			}
		});
		 fileContentsToBeSort.add(0, headerColumnStr);
		return fileContentsToBeSort;
	}
	
	private Integer getIndexFromString(String columnStr, int columnTypeToBeSortPosition, final Map<Integer, Integer> sortedOrderMap ){
		String[] columns = columnStr.split(",");
		Integer index = sortedOrderMap.get(Integer.valueOf(columns[columnTypeToBeSortPosition]));
		return index;
	}
	/** 
	 * Get position of sortedTypecolumn in header columns
	 * **/
	private int getSortColumnPositionOfHeader(String headerColumnStr, String sortedColumnType) {
		String[] headerColumns = headerColumnStr.split(",");
		int columnPosition =0;
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		for(String column: headerColumns){
			headerMap.put(column, columnPosition++);
		}
		if(headerMap.get(sortedColumnType) != null){
			return headerMap.get(sortedColumnType);
		} else {
			throw new FileReaderException("Column type "+ sortedColumnType +" not found in file");
		}
	}
	

}
