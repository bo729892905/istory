package cn.bluemobi.controller.app;

import java.util.ArrayList;
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
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.RelateService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App微电影接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppMicroFilmController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private RelateService relateService;
	/**
	 * 微电影列表
	 */
	@RequestMapping(value = "app/getMicroFilmList", method = RequestMethod.POST)
	public void microFilmService(String filmType,String memberId,String type,String myId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
			page.setPageCount(-1);
			//查询分页数据
			List<MicroFilm> list = microFilmService.getMicroFilmListByPage(filmType,memberId,type,myId,page);
		    list = list==null ? new ArrayList<MicroFilm>():list;
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
	 * 增加电影播放次数
	 */
	@RequestMapping(value = "app/addFilmPlayCount", method = RequestMethod.POST)
	public void addFilmPlayCount(String filmId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			microFilmService.addFilmPlayCount(filmId);
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
	 * 查找电影详情
	 */
	@RequestMapping(value = "app/getMicroFilm", method = RequestMethod.POST)
	public void getMicroFilm(String filmId,String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(filmId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//获取电影详情
				MicroFilm microFilm =  microFilmService.getMicroFilmForApp(filmId,memberId);
				data.putInData("microFilm",microFilm);
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
	 * 微电影删除
	 */
	@RequestMapping(value = "app/deleteFilm", method = RequestMethod.POST)
	public void deleteFilm(String filmId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(filmId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				microFilmService.deleteMicroFilm(filmId);
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
	 * 新建微电影
	 */
	@RequestMapping(value = "app/addMicroFilm", method = RequestMethod.POST)
	public void addMicroFilm(String title,   String theme,
			                 String director,String scriptwriter,
			                 String actor,   String content,
			                 String vedioUrl,String cover1,
			                 MultipartRequest re,
			                 String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//上传章节文件
			MultipartFile coverFile = re.getFile("cover");
			Map<String,Object> coverMap = FileController.uploadFile(coverFile,"2");
			//判断参数是否完整
			if(TextHelper.isNullOrEmpty(title) ||  TextHelper.isNullOrEmpty(vedioUrl) || TextHelper.isNullOrEmpty(memberId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//保存电影
				String cover = TextHelper.isNullOrEmpty(coverMap.get("data"))?cover1:coverMap.get("data").toString();
				microFilmService.addMicroFilm(title,theme,director,scriptwriter,actor,content,vedioUrl,memberId,cover);
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
