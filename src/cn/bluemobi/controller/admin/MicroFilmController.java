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
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 微电影管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/microFilm/")
public class MicroFilmController extends BaseController {
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入微电影列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,MicroFilm microFilm) {
		Map<String,Object> map = microFilmService.getMicroFilmListByPage(page,microFilm);
		request.setAttribute("microFilmList", map.get("microFilmList"));
		request.setAttribute("page", map.get("page"));
		return "admin/microFilm/list.jsp";
	}
	
	/**
	 * 电影状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				microFilmService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改微电影状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 删除电影
	 */
	@RequestMapping("deleteMicroFilm")
	public void deleteMicroFilm(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					microFilmService.deleteMicroFilm(idArr[i]);
				}
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除微电影");
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 进入电影编辑页面
	 */
	@RequestMapping("goEditFilm")
	public String goEditFilm(String id) {
		MicroFilm  microFilm = microFilmService.getMicroFilmById(id);
		request.setAttribute("microFilm", microFilm);
		return "admin/microFilm/edit.jsp";
	}
	/**
	 * 进入电影添加页面
	 */
	@RequestMapping("goAddFilm")
	public String goAddFilm() {
		return "admin/microFilm/add.jsp";
	}
	/**
	 * 编辑电影
	 */
	@RequestMapping("save")
	public void save(MicroFilm mf) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			mf.setReleaseId(admin.getId());
			microFilmService.save(mf);
			map.put(STATUS, SUCCESS);
			systemLogService.addSystemLog(admin, "修改微电影");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
}
