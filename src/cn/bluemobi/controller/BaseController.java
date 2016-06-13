package cn.bluemobi.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.util.helper.JsonHelper;


/**
 * 父类控制器，为所有子类控制器提供公共方法
 * 
 * @author 雷攀
 * 
 */
public class BaseController {
	/**
	 * 后台错误视图
	 */
	protected final static String ERROR_VIEW_ADMIN = "/admin/error/404";
	protected final static String ERROR_VIEW_STY = "/sty/error/404";

	protected final static String ERROR_VIEW_WEIXIN = "/weixin/error/404";

	protected final static String AMIND_LOGIN_URL = "redirect:"+BlueMobiConstant.domain+"admin/login.htm?uuid="+UUID.randomUUID();
	/**
	 * 前台错误视图
	 */
	protected final static String ERROR_VIEW = "";
	protected final static int SUCCESS = 1;// 成功
	protected final static int FAIL = 0;// 失败
	protected final static int EMPTY = 2;// 空
	protected final static int ERROR = 3;// 失败
	protected final static int FORMAT = 4;// 格式
	protected final static int SIZE = 5;// 大小
	protected final static int EXIST = 6;// 已经存在
	protected final static int PROPROTION = 7;// 图片比例
	protected final static String DATA = "data";// 数据
	protected final static String STATUS = "status";//状态

	/**
	 * ajax请求或者客户端请求,将返回的json数据
	 * 
	 * @param obj
	 */
	public void outJSON(Object obj) {
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (obj.getClass().getPackage().toString().indexOf("java.lang") != -1) {
				out.print(obj);
			} else {
				out.write(JsonHelper.convertToJSON(obj,true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				new Exception(e);
			}
		}
	}

	
	
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected HttpSession session = null;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	/**
	 * 格式化时间
	 * @param date 日期
	 * @param pattern 格式
	 * @return 时间字符串
	 */
	public static String formatDate(Date date, String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 格式化时间
	 * @param date 日期
	 * @return 时间字符串
	 */
	public String formatDate(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
}
