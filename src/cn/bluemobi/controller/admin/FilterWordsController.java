package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.FilterWords;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.FilterWordsService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 过滤字
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/filterWords/")
public class FilterWordsController extends BaseController {

	@Autowired
	private FilterWordsService filterWordsService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入过滤字列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,FilterWords filterWords) {
		Map<String,Object> map = filterWordsService.getFilterWordsListByPage(page,filterWords);
		request.setAttribute("filterWordsList", map.get("filterWordsList"));
		request.setAttribute("page", map.get("page"));
		return "admin/comment/filterList.jsp";
	}
	
	/**
	 * 新增过滤字
	 */
	@RequestMapping("add")
	public void add(String filter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(filter)){
				FilterWords fw = filterWordsService.getFilterWordsByFilter(filter);
				if(TextHelper.isNullOrEmpty(fw)){
					//如果为空就可以添加，非空就提示已经存在了
					filterWordsService.addFilterWords(filter);
					map.put(STATUS, SUCCESS);
					Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
					systemLogService.addSystemLog(a, "新增过滤字");
				}else{
					map.put("msg", "repeat");
					map.put(STATUS, ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 删除过滤字
	 */
	@RequestMapping("deleteFilterWords")
	public void deleteFilterWords(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(id)){
				filterWordsService.deleteFilterWords(id);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "删除过滤字");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		}
	}
}
