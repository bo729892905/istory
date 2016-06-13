package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.controller.admin.FileController;
import cn.bluemobi.entity.Relate;
import cn.bluemobi.entity.ScriptChapter;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.ScriptRole;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.RelateService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App剧本工厂接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppScriptFactoryController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private RelateService relateService;
	/**
	 * 剧本列表
	 */
	@RequestMapping(value = "app/getScriptFactoryList", method = RequestMethod.POST)
	public void getScriptFactoryList(String memberId,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
			page.setPageCount(-1);
			List<ScriptFactory> list = scriptFactoryService.getScriptFactoryListPage(type,memberId,page);
		    list = list==null ? new ArrayList<ScriptFactory>():list;
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
	 * 获取剧本场次列表
	 */
	@RequestMapping(value = "app/getScriptChapterList", method = RequestMethod.POST)
	public void getScriptChapterList(String scriptId,String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(scriptId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				ScriptFactory sf = scriptFactoryService.getScriptFactory(scriptId,memberId);
				//获取所有章节
				List<ScriptChapter> list = scriptFactoryService.getAllChapter(scriptId,memberId);
				list = list==null ? new ArrayList<ScriptChapter>():list;
				Relate r = relateService.getRelateByScript(scriptId);
				map.put("relate",r);
				map.put("scriptFactory",sf);
				map.put("list", list);
				data.putInData("script",map);
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
	 * 脚本详情接口
	 */
	@RequestMapping(value = "app/getScriptChapter", method = RequestMethod.POST)
	public void getScriptChapter(String chapterId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(chapterId) ){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				ScriptChapter sc = scriptFactoryService.getScriptChapterById(chapterId);
				data.putInData("scriptChapter",sc);
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
	 * 添加角色
	 */
	@RequestMapping(value = "app/addScriptRole", method = RequestMethod.POST)
	public void addScriptRole(ScriptRole sr) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//用户id为空，则提示错误
			if(TextHelper.isNullOrEmpty(sr.getMemberId())){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				if(TextHelper.isNullOrEmpty(sr.getScriptId())){
					sr.setScriptId(null);
				}
				Long id = scriptFactoryService.addScriptRole(sr);
				data.setStatus(SUCCESS);
				data.putInData("roleId",id);
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
	 * 新建和编辑剧本
	 */
	@RequestMapping(value = "app/addScript", method = RequestMethod.POST)
	public void addScript(String scriptId,String title,String content,String memberId,MultipartRequest re,String roleIds,String cover1) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//获取封面file
			MultipartFile coverFile = re.getFile("cover");
			Map<String,Object> coverMap = FileController.uploadFile(coverFile,"2");
			Long id = 0L;
			if(TextHelper.isNullOrEmpty(scriptId)){//新增
				if(TextHelper.isNullOrEmpty(title) || TextHelper.isNullOrEmpty(content) || TextHelper.isNullOrEmpty(memberId) || (TextHelper.isNullOrEmpty(coverFile)&&TextHelper.isNullOrEmpty(cover1))){
					data.setStatus(FAIL);
					data.setMsg(PARAM_ERROR);
				}else{
					ScriptFactory sf = new ScriptFactory();
					sf.setTitle(title);
					sf.setContent(content);
					sf.setReleaseId(Long.valueOf(memberId.trim()));
					sf.setCover(TextHelper.isNullOrEmpty(coverMap.get("data"))?cover1:coverMap.get("data").toString());
					//sf.setAuthorType("0"); 数据库默认为0 是用户  1是管理员
					//第一次，设置成发布状态
					sf.setStatus("1");
				    id = scriptFactoryService.addScript(sf);
					data.setStatus(SUCCESS);
					data.putInData("id",id);
					}
			}else{//编辑
				id = Long.valueOf(scriptId);
				//获取剧本信息
				ScriptFactory sf = scriptFactoryService.getScriptFactory(scriptId,"");
				sf.setTitle(TextHelper.isNullOrEmpty(title)?sf.getTitle():title);
				sf.setContent(TextHelper.isNullOrEmpty(content)?sf.getContent():content);
				sf.setCover(TextHelper.isNullOrEmpty(coverMap.get("data"))?sf.getCover():coverMap.get("data").toString());
				sf.setTitle(TextHelper.isNullOrEmpty(title)?sf.getTitle():title);
				scriptFactoryService.updateScript(sf);
				
				data.setStatus(SUCCESS);
			}
			if(!TextHelper.isNullOrEmpty(roleIds)){
				//将角色关联剧本id
				String[] idArray = roleIds.split(",");
				for (int i = 0; i < idArray.length; i++) {
					scriptFactoryService.updateRoleScriptId(id.toString(),idArray[i].trim());
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
	/**
	 * 发布章节
	 */
	@RequestMapping(value = "app/addScriptChapter", method = RequestMethod.POST)
	public void addScriptChapter(String scriptId,String text,String status) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if( !TextHelper.isNullOrEmpty(text)){
				Map<String,Object> map = new HashMap<String,Object>();
				//新增章节
				Long chapterId = scriptFactoryService.addScriptChapter(scriptId,text,status);
				ScriptFactory sf = scriptFactoryService.getScriptFactory(scriptId,"");
				//获取所有章节
				List<ScriptChapter> list = scriptFactoryService.getAllChapter(scriptId,"");
				list = list==null ? new ArrayList<ScriptChapter>():list;
				Relate r = relateService.getRelateByScript(scriptId);
				map.put("relate",r);
				map.put("scriptFactory",sf);
				map.put("list", list);
				map.put("chapterId", chapterId);
				data.putInData("map",map);
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
	 * 获取角色列表
	 */
	@RequestMapping(value = "app/getScriptRoleList", method = RequestMethod.POST)
	public void getScriptRoleList(String scriptId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			List<ScriptRole> list = scriptFactoryService.getScriptRoleList(scriptId);
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
	 * 
	 * 
	 * 删除
	 */
	@RequestMapping(value = "app/deleteScriptChapter", method = RequestMethod.POST)
	public void deleteScriptChapter(String chapterId,String scriptId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(chapterId) &&  TextHelper.isNullOrEmpty(scriptId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				if(!TextHelper.isNullOrEmpty(chapterId)){
					String[] idArr = chapterId.split(",");
					for (int i = 0; i < idArr.length; i++) {
						scriptFactoryService.deleteScriptChapter(idArr[i]);
						data.setStatus(SUCCESS);
					}
				}else{
					scriptFactoryService.deleteScriptFactory(scriptId);
					data.setStatus(SUCCESS);
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
	
	/**
	 * 剧本发布或者保存
	 */
	@RequestMapping(value = "app/updateChapterStatus", method = RequestMethod.POST)
	public void updateChapterStatus(String chapterId,String status) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			String[] idArray = chapterId.split(",");
			if(!TextHelper.isNullOrEmpty(chapterId) && !TextHelper.isNullOrEmpty(status) ){
				for (int i = 0; i < idArray.length; i++) {
					scriptFactoryService.updateChapterStatus(idArray[i],status);
				}
				data.setStatus(SUCCESS);
			}else{
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
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
