package cn.bluemobi.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.Report;
import cn.bluemobi.entity.Tag;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.ReportService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App举报接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppReportController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private ReportService reportService;
	
	/**
	 * 举报菜单
	 */
	@RequestMapping(value = "app/reportList", method = RequestMethod.POST)
	public void reportList() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			List<Tag> list = reportService.getReportTagList();
			data.putInData("list",list);
			data.setStatus(SUCCESS);
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	/**
	 * 举报
	 * 
	 * @param type 1是故事岛 2是剧本工厂3是微电影
	 * @param tagId 标签id
	 * 
	 */
	@RequestMapping(value = "app/report", method = RequestMethod.POST)
	public void report(String releaseId,String beReportId,String type,String tagId,String memberId ) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(memberId) || TextHelper.isNullOrEmpty(releaseId) || TextHelper.isNullOrEmpty(beReportId) || TextHelper.isNullOrEmpty(type)|| TextHelper.isNullOrEmpty(tagId) ){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Report r = reportService.getReport(beReportId,type,memberId);
				if(TextHelper.isNullOrEmpty(r)){
					reportService.addReport(releaseId,beReportId,type,tagId,memberId);
					data.setStatus(SUCCESS);
				}else{
					data.setStatus(FAIL);
					data.setMsg("already_report");
				}
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
