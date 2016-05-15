package com.qaapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MatchDegreeAndId {
	public double matchDegree;
	public Long id;

	public MatchDegreeAndId(double matchDegree, Long id) {
		this.matchDegree = matchDegree;
		this.id = id;
	}

	/**
	 * 按照匹配程度的降序排列的一个比较器
	 * 
	 * @return
	 */
	public static Comparator<MatchDegreeAndId> getReverseComparator() {
		return new Comparator<MatchDegreeAndId>() {

			@Override
			public int compare(MatchDegreeAndId o1, MatchDegreeAndId o2) {
				return Collections.reverseOrder().compare(o1.matchDegree,
						o2.matchDegree);
			}
		};
	}

	/**
	 * 把一个特定匹配度插入ids中 （如果它小于排名前matchCount的那些值，那么就插不进去）
	 * 
	 * @param ids
	 * @param matchDegree
	 * @param id
	 */
	public static void insertIntoMDIS(MatchDegreeAndId mdi,
			List<MatchDegreeAndId> mdis, int matchCount) {

		mdis.add(mdi);

		Collections.sort(mdis, MatchDegreeAndId.getReverseComparator());

		if (mdis.size() <= matchCount) {
			return;
		} else {
			mdis.remove(matchCount);
		}
	}

	@Override
	public String toString() {
		return "MatchDegreeAndId:" + matchDegree;
	}
	
	public static void main(String[] args) {
		List<MatchDegreeAndId> mdis = new ArrayList<MatchDegreeAndId>();
		int matchCount = 5;
		double[] degrees = new double[] { 1.4, 4.6, 3.7, 2.6, 4.5, 2.4, 3.1, 5.9 };
		for (double d : degrees) {
			insertIntoMDIS(new MatchDegreeAndId(d, 1L), mdis, matchCount);
		}
		System.out.println(mdis);
	}
}
