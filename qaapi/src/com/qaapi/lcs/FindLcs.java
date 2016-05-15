package com.qaapi.lcs;

public class FindLcs {
	
	/**
	 * 求最长公共子序列的长度
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int find(String str1, String str2) {
		int m = str2.length();
		int n = str1.length();
		int[][] opt = new int[m + 1][n + 1];

		for (int i = 0; i <= str2.length(); i++) {
			opt[i][0] = 0;
		}

		for (int j = 0; j <= str1.length(); j++) {
			opt[0][j] = 0;
		}

		for (int j = 1; j <= str1.length(); j++) {
			for (int i = 1; i <= str2.length(); i++) {
				if (str2.charAt(i - 1) == str1.charAt(j - 1)) {
					opt[i][j] = opt[i - 1][j - 1] + 1;
				} else {
					opt[i][j] = (opt[i - 1][j] >= opt[i][j - 1] ? opt[i - 1][j] : opt[i][j - 1]);
				}
			}
		}

		return opt[m][n];
	}
	
	public static void main(String[] args) {
		System.out.println(find("asradffchedf","rtertrachepii"));
		System.out.println(find("南京邮电大学士大夫还是和额哈哈","打法看电视士大诶发夫还是大法师地方"));
	}
}
