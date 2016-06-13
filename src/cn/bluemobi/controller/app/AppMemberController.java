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
import cn.bluemobi.controller.PushExample;
import cn.bluemobi.controller.admin.FileController;
import cn.bluemobi.entity.Attention;
import cn.bluemobi.entity.Letter;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.AttentionService;
import cn.bluemobi.service.CommentService;
import cn.bluemobi.service.LetterService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.PushLogService;
import cn.bluemobi.service.SubscibeService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App用户接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppMemberController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private AttentionService  attentionService;
	@Autowired
	private SubscibeService subscibeService;
	@Autowired
	private LetterService letterService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PushLogService pushLogService;
	/**
	 * 关注 粉丝 订阅数量
	 */
	@RequestMapping(value = "app/getAttentionAndSubscibe", method = RequestMethod.POST)
	public void getAttentionAndSubscibe(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(memberId)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				Map<String,Object> map = new HashMap<String, Object>();
				//关注数量
				Integer attentionNum =  attentionService.getAttentionNum(memberId);
				//粉丝数量
				Integer beAttentionNum = attentionService.getBeAttentionNum(memberId,"");
				//订阅数量
				Integer subscibeNum = subscibeService.getSubscibeNum(memberId);
				Integer updateNum = 0;
				//订阅更新数量
			    updateNum = subscibeService.getUpdateNum(memberId); 
				
				map.put("attentionNum", attentionNum);
				map.put("beAttentionNum", beAttentionNum);
				map.put("subscibeNum", subscibeNum);
				map.put("updateNum", updateNum);
				
				data.putInData("attentionAndSubscibe",map);
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
	 * 关注和粉丝
	 */
	@RequestMapping(value = "app/getAttention", method = RequestMethod.POST)
	public void getAttention(String memberId,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
			page.setPageCount(-1);
			
			if (TextHelper.isNullOrEmpty(memberId)&&TextHelper.isNullOrEmpty(type)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				//根据type判断是查1关注 或者0粉丝
				List<Attention> list = attentionService.getAttention(memberId,type,page);
				list = list ==null ? new ArrayList<Attention>():list;
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
	 * 添加关注或者取消关注
	 */
	@RequestMapping(value = "app/addOrCancelAttention", method = RequestMethod.POST)
	public void addOrCancelAttention(String memberId,String type,String beAttentionId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			
			if (TextHelper.isNullOrEmpty(memberId)&&TextHelper.isNullOrEmpty(type)&&TextHelper.isNullOrEmpty(beAttentionId)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				//先查询用户是否更关注过，
				Attention a =  attentionService.getAttentionByMemberId(memberId,beAttentionId);
				if("1".equals(type)){
					if(!TextHelper.isNullOrEmpty(a)){
						data.setStatus(FAIL);
						data.setMsg("already_attention");
					}else if(memberId.equals(beAttentionId)){
						data.setStatus(FAIL);
						data.setMsg("can't_attention_yourself");
					}else{
						//添加关注
						attentionService.addAttention(memberId,beAttentionId);
						data.setStatus(SUCCESS);
						
						//推送关注信息    
                        //type="1";
						Member member =  memberService.getMemberById(memberId);
						String content = "";
						if(!TextHelper.isNullOrEmpty(member)){
							if(TextHelper.isNullOrEmpty(member.getName())){
								content = "有新用户关注了你";
							}else{
								content = member.getName()+"关注了你";
							}
						}
						PushExample.testSendPushApp(new String[]{"ID_"+beAttentionId}, content,"1");
						
					}
				}else{
					//取消关注
					attentionService.cancelAttention(memberId,beAttentionId);
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
	 * 上传图片或文件的单独接口
	 */
	@RequestMapping(value = "app/uploadFile", method = RequestMethod.POST)
	public void releaseStory(MultipartRequest re,String fileType ) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//上传章节文件  2是图片 3是音频  4是视频
			MultipartFile uploadFile = re.getFile("fileName");
			if(TextHelper.isNullOrEmpty(fileType)|| TextHelper.isNullOrEmpty(uploadFile)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Map<String,Object> uploadMap = FileController.uploadFile(uploadFile,fileType);
				String fileUrl = TextHelper.isNullOrEmpty(uploadMap.get("data"))?"":uploadMap.get("data").toString();
				data.putInData("fileUrl",fileUrl);
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
	 * 获取用户个人信息
	 */
	@RequestMapping(value = "app/getMemberInfo", method = RequestMethod.POST)
	public void getMemberInfo(String memberId,String myId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(memberId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Member member =	memberService.getMemberByIdForApp(memberId.trim());
				//关注数量
				Integer attentionNum =  attentionService.getAttentionNum(memberId);
				//粉丝数量
				Integer beAttentionNum = attentionService.getBeAttentionNum(memberId,"");
				//订阅数量
				Integer subscibeNum = subscibeService.getSubscibeNum(memberId);
				//未关注
				String isAttention = "0";
				
				if(!TextHelper.isNullOrEmpty(myId)){
					//是否关注
					Attention a = attentionService.getAttentionByMemberId(myId,memberId);
					if(!TextHelper.isNullOrEmpty(a)){
						isAttention  = "1";
					}
				}
				if(!TextHelper.isNullOrEmpty(member)){
					member.setAttentionNum(attentionNum);
					member.setBeAttentionNum(beAttentionNum);
					member.setSubscibeNum(subscibeNum);
					member.setIsAttention(isAttention);
					data.putInData("member",member);
					data.setStatus(SUCCESS);
				}else{
					data.setMsg("member_error");
					data.setStatus(FAIL);
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
	 * 检查用户数据更新情况的接口。便于和推送保持一致
	 */
	@RequestMapping(value = "app/checkUpdate", method = RequestMethod.POST)
	public void checkUpdate(String fansTime,String letterTime,String commentTime,String messageTime,String attentionTime, String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Integer beAttentionNum = 0;
			Integer commentNum = 0;
			Integer messageNum = 0;
			Integer myAttentionNum =0;
			List<Letter> list = new ArrayList<Letter>();
			//订阅数量
			Integer subscibeNum = subscibeService.getUpdateNum(memberId);
			if(!TextHelper.isNullOrEmpty(fansTime)){
				//粉丝数量
				 beAttentionNum = attentionService.getBeAttentionNum(memberId,fansTime);
			}
			if(!TextHelper.isNullOrEmpty(letterTime)){
				//私信
				list = letterService.getLetterContactListByTime(memberId,letterTime);
				
			}
			if(!TextHelper.isNullOrEmpty(commentTime)){
				//评论更新数量
				commentNum = commentService.getMyCommentByTime(memberId,commentTime);
			}
			if(!TextHelper.isNullOrEmpty(messageTime)){
				//消息更新数量
				messageNum = pushLogService.getPushLogListByTime(memberId,messageTime);
			}
			if(!TextHelper.isNullOrEmpty(myAttentionNum)){
				//我的关注更新数量
				myAttentionNum = attentionService.getAttentionStoryByTime(memberId,attentionTime);
			}
			data.putInData("beAttentionNum",beAttentionNum);
			data.putInData("subscibeNum",subscibeNum);
			data.putInData("list",list==null?new ArrayList<Letter>():list);
			data.putInData("commentNum",commentNum);
			data.putInData("messageNum",messageNum);
			data.putInData("myAttentionNum",myAttentionNum);
			data.setStatus(SUCCESS);
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
}
