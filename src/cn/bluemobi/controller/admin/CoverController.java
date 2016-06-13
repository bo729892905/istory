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
import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.CoverService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 封面管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/cover/")
public class CoverController extends BaseController {

	@Autowired
	private CoverService coverService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入广告列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page) {
		//获取封面list
		Map<String,Object> map = coverService.getAdvertisementList(page);
		request.setAttribute("coverList", map.get("coverList"));
		request.setAttribute("page", map.get("page"));
		return "admin/cover/list.jsp";
	}
	/**
	 * 验证封面数量
	 */
	@RequestMapping("getNumber")
	public void getNumber() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Integer number =  coverService.getTotalCover();
			map.put("number", number);
			map.put(STATUS,SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 新增封面
	 * @return
	 */
	@RequestMapping("goAddCover")
	public String goAddCover() {
		return "admin/cover/add.jsp";
	}

	/**
	 * 新增封面保存
	 * @return
	 */
	@RequestMapping("save")
	public void save(String cover,String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Integer number =  coverService.getTotalCover();
			if(number>=30){
				map.put("msg","over_total");
			}else{
				Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				if(!TextHelper.isNullOrEmpty(admin)){
					if(TextHelper.isNullOrEmpty(id)){
						//新增
						coverService.save(cover,admin.getId());
						Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
						systemLogService.addSystemLog(a, "新增封面");
					}else{
						//更新
						coverService.updateCover(cover,id,admin.getId());
						Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
						systemLogService.addSystemLog(a, "修改封面资料");
					}
					map.put(STATUS, SUCCESS);
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
	 * 编辑广封面
	 * @return
	 */
	@RequestMapping("goEditCover")
	public String goEditCover(String id) {
	    Cover cover = coverService.getCoverById(id);
	    request.setAttribute("cover", cover);
		return "admin/cover/edit.jsp";
	}
	/**
	 * 删除封面
	 */
	@RequestMapping("deleteCover")
	public void deleteCover(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					coverService.deleteCover(idArr[i]);
				}
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除封面");
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		}
	}

}
