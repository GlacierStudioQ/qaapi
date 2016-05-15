package com.qaapi.kei;

import java.util.ArrayList;
import java.util.List;

public class FindKei {
	
	/**
	 * 返回两个list交集的长度
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static int findInList(List<String> l1, List<String> l2) {
		List<String> result = new ArrayList<String>();
		result.addAll(l1);
		result.retainAll(l2);
		return result.size();
	}
}
