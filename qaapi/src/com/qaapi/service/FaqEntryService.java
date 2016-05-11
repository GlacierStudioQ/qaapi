package com.qaapi.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaapi.bean.CutPage;
import com.qaapi.bean.FaqEntry;
import com.qaapi.dao.FaqDao;
import com.qaapi.util.ReturnJson;

import static com.qaapi.util.GlobeStatus.*;
import static com.qaapi.util.QaapiStatic.*;

/**
 * 
 * FaqEntry的数据库操作等方法
 * 
 * @author IceAsh
 *
 */
@Service("faqEntryService")
public class FaqEntryService {

	@Autowired
	FaqDao faqDao;

	/**
	 * 增加一个问答
	 * 
	 * @param entry
	 * @return
	 */
	public JSONObject save(FaqEntry entry){
		
		if(entry == null || StringUtils.isEmpty(entry.getAnswer()) || StringUtils.isEmpty(entry.getQuestion())){
			return ReturnJson.notok(entry, "参数不能为空", REQ_ERROR_400);
		}
		
		Long entryId = (Long) faqDao.save(entry);
		entry.setId(entryId);
		
		return ReturnJson.ok(entry, "增加问答成功");
	}
	
	/**
	 * 删除一个问答
	 * 
	 * @param entryId
	 * @return
	 */
	public JSONObject delete(FaqEntry entry){
		if(entry == null || entry.getId() == null){
			return ReturnJson.notok(entry, "id不能为空", REQ_ERROR_400);
		}
		Long entryId = entry.getId();
		FaqEntry entryOld = faqDao.getById(entryId);
		if(entryOld == null){
			return ReturnJson.notok(entryId, "问答不存在", REQ_ERROR_400);
		}
		faqDao.delete(entryId);
		return ReturnJson.ok(entryOld, "删除问答成功");
	}
	
	/**
	 * 更新一个问答
	 * 
	 * @param entry
	 * @return
	 */
	public JSONObject update(FaqEntry entry) {
		if(entry == null || entry.getId() == null	   || StringUtils.isEmpty(entry.getAnswer()) || StringUtils.isEmpty(entry.getQuestion())){
			return ReturnJson.notok(entry, "参数不能为空", REQ_ERROR_400);
		}
		FaqEntry entryOld = faqDao.getById(entry.getId());
		if(entryOld == null){
			return ReturnJson.notok(entry, "问答不存在", REQ_ERROR_400);
		}
		entryOld.setAnswer(entry.getAnswer());
		entryOld.setQuestion(entry.getQuestion());
		faqDao.update(entryOld);
		
		return ReturnJson.ok(entryOld, "更新问答成功");
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
		
		if(cutPage.getDateCount() == null){
			List countList = faqDao.findByHql("select count(*) from FaqEntry");
			Long countLong = (Long) countList.get(0);
			Integer dateCount = new Integer(countLong.toString());
			cutPage.setDateCount(dateCount);
			cutPage.setPageCount(dateCount / DEFAULT_PAGE_SIZE);
		}
		
		List<FaqEntry> faqEntries = faqDao.loadAll("", cutPage.getPageSize() * (cutPage.getNowPage() - 1), cutPage.getPageSize());
		
		JSONArray faqEntriesJson = JSONArray.fromObject(faqEntries);
		return ReturnJson.ok(faqEntriesJson, "成功返回数据");
	}
	
	//============setter&getter============
	
	public FaqDao getFaqDao() {
		return faqDao;
	}

	public void setFaqDao(FaqDao faqDao) {
		this.faqDao = faqDao;
	}
	
}

