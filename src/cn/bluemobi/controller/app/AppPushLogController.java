package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.PushLog;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.PushLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 系统消息接口
 * @author xiazf
 *
 */
@Controller
public class AppPushLogController extends AppController {
	@Autowired
	private PushLogService pushLogService;
	
	/**
	 * 系统消息列表
	 */
	@RequestMapping(value = "app/getPushLogList", method = RequestMethod.POST)
	public void getPushLogList(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(memberId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Page page = new Page();
				page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
				page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
				page.setPageCount(-1);
				//查询分页数据
				List<PushLog> list = pushLogService.getPushLogListByPage(memberId,page);
				list = list==null ? new ArrayList<PushLog>():list;
				data.putInData("list",list);
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
	/**
	 * 删除系统消息
	 */
	@RequestMapping(value = "app/deletePushLog", method = RequestMethod.POST)
	public void deletePushLog(String logId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(logId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				pushLogService.deletePushLog(logId);
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
