package com.qaapi.util;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 连接到微信自动回复的工具类
 * @author IceAsh
 *
 */
public class WechatUtil {
	/*
	 * 接收文本信息的xml键固定值
	 */
	public static String WX_ToUserName = "ToUserName"; // 开发者微信号
	public static String WX_FromUserName = "FromUserName"; // 发送方帐号（一个OpenID）
	public static String WX_CreateTime = "CreateTime"; // 消息创建时间 （整型）
	public static String WX_MsgType = "MsgType"; // text
	public static String WX_Content = "Content"; // 文本消息内容
	public static String WX_MsgId = "MsgId"; // 消息id，64位整型
	

	/**
	 * 从请求体中获取xml数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseReqXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

	public static String makeRespXml(Map<String, String> reqXmlMap, String content){
		Document doc = DocumentHelper.createDocument();
		Element xml = doc.addElement("xml");
		
		// 返回的时候就把to和from调换位置
		xml.addElement(WX_ToUserName).addCDATA(reqXmlMap.get(WX_FromUserName));
		xml.addElement(WX_FromUserName).addCDATA(reqXmlMap.get(WX_ToUserName));
		xml.addElement(WX_CreateTime).addText("" + (new Date()).getTime());
		xml.addElement(WX_MsgType).addCDATA("text");
		xml.addElement(WX_Content).addCDATA(content);
		
		return doc.asXML();
	}
	
	/**
	 * sha1加密算法
	 * 
	 * @author http://www.sha1-online.com/sha1-java/
	 * 
	 * @param input
	 * @return sha1 str
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		Map<String, String> reqXmlMap = new HashMap<String, String>();
		reqXmlMap.put(WX_ToUserName, "qjy");
		reqXmlMap.put(WX_FromUserName, "dian nao");
		
		System.out.println(makeRespXml(reqXmlMap, "test"));
	}
}
