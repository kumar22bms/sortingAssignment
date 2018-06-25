package com.hm;

import com.hm.facade.SortingFacade;

public class SortFileApplication {

	public static void main(String[] args) {
		 new SortingFacade().performFileSorting(args);
	}
}
