package com.qaapi.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaapi.bean.CutPage;
import com.qaapi.bean.FaqEntry;
import com.qaapi.bean.Feedback;
import com.qaapi.dao.BaseDao;
import com.qaapi.dao.FeedbackDao;
import com.qaapi.util.ReturnJson;

import static com.qaapi.util.GlobeStatus.*;
import static com.qaapi.util.QaapiStatic.*;

/**
 * 
 * Feedback的数据库操作等方法
 * 
 * @author IceAsh
 *
 */
@Service("feedbackService")
public class FeedbackService {

	@Autowired
	FeedbackDao feedbackDao;
	@Autowired
	BaseDao baseDao;

	
	/**
	 * 增加一个反馈
	 * 
	 * @param feedback
	 * @return
	 */
	public JSONObject save(Feedback feedback){
		
		if(feedback == null || feedback.getFaqId() == null || StringUtils.isEmpty(feedback.getUserQuestion())){
			return ReturnJson.notok(feedback, "参数不能为空", REQ_ERROR_400);
		}
		
		feedback.setCreateTime(new Date());
		Long entryId = (Long) feedbackDao.save(feedback);
		feedback.setId(entryId);
		
		return ReturnJson.ok(feedback, "反馈成功");
	}
	
	public JSONObject findByPage(CutPage cutPage) {
		
		if(cutPage == null){
			cutPage = new CutPage();
		}
		
		if(cutPage.getNowPage() == null){
			cutPage.setNowPage(1);
		}
		
		if(cutPage.getPageSize() == null){
			cutPage.setPageSize(DEFAULT_PAGE_SIZE);
		}
		
		if(cutPage.getDataCount() == null){
			List countList = feedbackDao.findByHql("select count(*) from Feedback");
			Long countLong = (Long) countList.get(0);
			Integer dateCount = new Integer(countLong.toString());
			cutPage.setDataCount(dateCount);
			int  pageSize = cutPage.getPageSize();
			cutPage.setPageCount(dateCount % pageSize == 0 ?
											dateCount / pageSize : 
											dateCount / pageSize + 1);
		}
		
		cutPage.format();// 避免非正常的数值出现
		
		// 关联对应的FaqEntry
		String hql = "select d,e from Feedback d ,FaqEntry e where d.faqId = e.id order by d.createTime desc";
		List<Object[]> feedBacksAndfaqEntries =  (List<Object[]>) baseDao.findByHql(hql, cutPage.getPageSize() * (cutPage.getNowPage() - 1), cutPage.getPageSize());
		
		JSONArray feedbacksJson = new JSONArray();
		for(Object[] feedbackAndfaqEntrie : feedBacksAndfaqEntries){
			JSONObject feedbackJson = JSONObject.fromObject(((Feedback)feedbackAndfaqEntrie[0]));
			feedbackJson.put("faqEntry", ((FaqEntry)feedbackAndfaqEntrie[1]));
			feedbacksJson.add(feedbackJson);
		}
		
		// List<Feedback> faqEntries = feedbackDao.findByHql("from Feedback order by createTime desc", cutPage.getPageSize() * (cutPage.getNowPage() - 1), cutPage.getPageSize());
		
		// 返回的json中不光要有问答的数据，还要有用于分页的cutPage类（放在other中）
		return ReturnJson.ok(feedbacksJson, "成功返回数据", JSONObject.fromObject(cutPage));
	}

	//============setter&getter============
	
	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}

