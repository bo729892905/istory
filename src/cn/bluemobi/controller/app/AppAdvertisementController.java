package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.Advertisement;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.AdvertisementService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App广告轮播接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppAdvertisementController extends AppController {
	@Autowired
	private AdvertisementService advertisementService;
	/**
	 * 轮播列表
	 */
	@RequestMapping(value = "app/getAdvertisement", method = RequestMethod.POST)
	public void getAdvertisement(String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(type)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				List<Advertisement> list = advertisementService.getAdvertisementListByType(type);
			
				data.putInData("list",list==null? new ArrayList<Advertisement>():list);
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
	 * 轮播图增加点击量
	 */
	@RequestMapping(value = "app/advertisementViewCount", method = RequestMethod.POST)
	public void advertisementViewCount(String id){
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(id)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//星故事
				advertisementService.addViewCount(id);
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
