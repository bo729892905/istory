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
import cn.bluemobi.controller.PushExample;
import cn.bluemobi.controller.admin.FileController;
import cn.bluemobi.entity.Letter;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.LetterService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 私信
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppLetterController extends AppController {
	@Autowired
	private LetterService letterService;
	@Autowired
	private MemberService memberService;
	/**
	 * 私信列表         
	 * 
	 * 
	 */
	@RequestMapping(value = "app/getLetterContactList", method = RequestMethod.POST)
	public void getLetterContactList(String memberId) {
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
				//获取私信联系人列表
				List<Letter> list = letterService.getLetterContactList(memberId,page);
				list = list==null ? new ArrayList<Letter>():list;
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
	 * 私信详情
	 */
	@RequestMapping(value = "app/getLetterRecordsList", method = RequestMethod.POST)
	public void getLetterRecordsList(String sendId,String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(memberId) || TextHelper.isNullOrEmpty(sendId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Page page = new Page();
				page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
				page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "20": getParameter("pageSize")));
				page.setPageCount(-1);
				//获取私信联系人列表
				List<Letter> list = letterService.getLetterRecordsList(sendId,memberId,page);
				list = list==null ? new ArrayList<Letter>():list;
				//更改状态为已读
				letterService.upDateRead(sendId,memberId);
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
	 * 发私信
	 */
	@RequestMapping(value = "app/sendLetter", method = RequestMethod.POST)
	public void sendLetter(String sendId,String receiveId,String content,String type,MultipartRequest re) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(receiveId) || TextHelper.isNullOrEmpty(sendId) || TextHelper.isNullOrEmpty(type)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				
				String pushText = "";
				Member member = memberService.getMemberById(sendId);
				if("2".equals(type)){//图片私信
					//获取图片
					MultipartFile imgFile = re.getFile("img");
					Map<String,Object> imgMap = FileController.uploadFile(imgFile,"2");
					content = TextHelper.isNullOrEmpty(imgMap.get("data"))?content:imgMap.get("data").toString();
					if(!TextHelper.isNullOrEmpty(member)){
						pushText = member.getName()+": [图片]";
					}
				}else{
					if(!TextHelper.isNullOrEmpty(member)){
						pushText = member.getName()+": "+content;
					}
				}
				letterService.sendLetter(sendId,receiveId,type,content);
				data.setStatus(SUCCESS);
				//推送私信
				PushExample.testSendPushApp(new String[]{"ID_"+receiveId}, pushText, "3");
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
