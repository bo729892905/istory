package cn.bluemobi.controller.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.SearchService;
import cn.bluemobi.util.text.TextHelper;

@Controller
@Scope("prototype")
public class AppSearchController extends AppController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "app/searchAll", method = RequestMethod.POST)
	public void searchAll(String memberId,String keywords,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo")==null?"1":getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize")==null?"5":getParameter("pageSize")));
			page.setPageCount(-1);
			if(TextHelper.isNullOrEmpty(keywords)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Map<String,Object> map = searchService.getAllBySearch(memberId,keywords,type,page);
				data.putInData("searchMap",map);
				data.setStatus(SUCCESS);
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
		
	}
	
}
	
