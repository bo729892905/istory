package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.AppController;
import cn.bluemobi.controller.PushExample;
import cn.bluemobi.controller.admin.FileController;
import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.FilePicture;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.StoryIslandChapter;
import cn.bluemobi.entity.StoryIslandClassify;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.AttentionService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.RelateService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.StoryIslandClassifyService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.service.SubscibeService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 故事岛接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppStoryIslandController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private StoryIslandClassifyService storyIslandClassifyService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private RelateService relateService;
	@Autowired
	private SubscibeService subscibeService;
	@Autowired
	private AttentionService attentionService;
	
	/**
	 * 故事列表
	 */
	@RequestMapping(value = "app/getStoryIslandList", method = RequestMethod.POST)
	public void getStoryIslandList(String memberId,String choiceness,String type,String classifyId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
			page.setPageCount(-1);
			List<StoryIsland> list = storyIslandService.getStoryIslandList(memberId,choiceness,classifyId,type,page);
		    list = list==null ? new ArrayList<StoryIsland>():list;
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
	 * 故事列表  分类
	 */
	@RequestMapping(value = "app/getStoryIslandClassifyList", method = RequestMethod.POST)
	public void getStoryIslandClassifyList(String type,String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "6": getParameter("pageSize")));
			page.setPageCount(-1);
			
			List<StoryIsland> choicenessList = storyIslandService.getStoryIslandList("","1","",type,page);
			choicenessList = choicenessList==null ? new ArrayList<StoryIsland>():choicenessList;
			
			List<StoryIslandClassify> classifylist = storyIslandClassifyService.getStoryIslandClassifyList(memberId);
			if(classifylist !=null){
				for (int i = 0; i < classifylist.size(); i++) {
					if(!TextHelper.isNullOrEmpty(classifylist.get(i).getId())){
						String classifyId = classifylist.get(i).getId().toString();
						List<StoryIsland> storyIslandList = storyIslandService.getStoryIslandList("","",classifyId,type,page);
						storyIslandList = storyIslandList==null ? new ArrayList<StoryIsland>():storyIslandList;
						classifylist.get(i).setStoryIslandList(storyIslandList);
					}
				}
			}
			
			classifylist = classifylist==null ? new ArrayList<StoryIslandClassify>():classifylist;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("choicenessList", choicenessList);
			map.put("classifylist", classifylist);
			data.putInData("listMap",map);
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
	 * 添加新的故事和章节
	 */
	@RequestMapping(value = "app/releaseStory", method = RequestMethod.POST)
	public void releaseStory(String title,String cover1, MultipartRequest re,String memberId,
							 String status,String storyIslandId,String chapterType,String textStory,
							 String classifyId,FilePicture fList,
							 String paper,String color,String strong,String em,String u,
							 String textAlign,String font,String isAnonymous,String chapterId
							){
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//上传章节文件
			MultipartFile imgStoryFile = re.getFile("imgStory");
			MultipartFile imgFile = re.getFile("img");
			MultipartFile voiceStoryFile = re.getFile("voiceStory");
			MultipartFile videoStoryFile = re.getFile("videoStory");
			Map<String,Object> imgStoryMap = FileController.uploadFile(imgStoryFile,"2");
			Map<String,Object> imgMap = FileController.uploadFile(imgFile,"2");
			Map<String,Object> voiceStoryMap = FileController.uploadFile(voiceStoryFile,"3");
			Map<String,Object> videoStoryMap = FileController.uploadFile(videoStoryFile,"4");
			if(TextHelper.isNullOrEmpty(memberId)||TextHelper.isNullOrEmpty(status)||(TextHelper.isNullOrEmpty(storyIslandId)&&TextHelper.isNullOrEmpty(classifyId))||TextHelper.isNullOrEmpty(chapterType)||(TextHelper.isNullOrEmpty(imgStoryFile)&&TextHelper.isNullOrEmpty(voiceStoryFile)&&TextHelper.isNullOrEmpty(videoStoryFile)&&TextHelper.isNullOrEmpty(textStory))){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//标题和封面就必须传进来了
				MultipartFile coverFile = re.getFile("cover");
				//2表示上传文件类型是图片，3是音频文件 ， 4是视频文件
				Map<String,Object> img1Map = FileController.uploadFile(coverFile,"2");
				String cover = TextHelper.isNullOrEmpty(img1Map.get("data"))?cover1:img1Map.get("data").toString();
				
				//将故事信息保存在实体里
				StoryIsland si = new StoryIsland();
				si.setCover(cover);
				si.setId(TextHelper.isNullOrEmpty(storyIslandId)?null:Long.valueOf(storyIslandId.trim()));
				si.setTitle(title);
				si.setReleaseId(TextHelper.isNullOrEmpty(memberId)?null:Long.valueOf(memberId.trim()));
				//app传过来出现回车影响程序执行，需要trim();
				si.setStatus(status.trim());
				si.setClassifyId(TextHelper.isNullOrEmpty(classifyId)?null:Integer.valueOf(classifyId.trim()));
				si.setChapterType(chapterType.trim());
				//是否匿名
				String name = "";
				
				if("1".equals(isAnonymous)){
					name= getAnonymousNameAndIcon();
					si.setAnonymousName(name);
					si.setAnonymousIcon(BlueMobiConstant.domain+"imgs/icon/"+name+".png");
					si.setIsAnonymous("1");
				}else{
					si.setIsAnonymous("0");
				}
				
				
				//将章节保存在实体里
				StoryIslandChapter sic = new StoryIslandChapter();
				sic.setStatus(status);
				sic.setChapterType(chapterType);
				if("1".equals(chapterType)){
					//如果内容全部为空，则不执行新增操作
					List<MultipartFile> fileList = fList.getFileList();
					// String paper,String color,String strong,String em,String u,
					// String textAlign font
					Map<String,String> map = getTextStory(textStory,fileList,color,strong,em,u,textAlign,font,paper);
					String text = map.get("text");
					String imgList = map.get("imgList");
					if(text.length()>50000){
						data.setStatus(FAIL);
						data.setMsg("text_too_long");
						return;
					}
					sic.setTextStory(text);
					if(!TextHelper.isNullOrEmpty(chapterId)){
						sic.setId(Long.valueOf(chapterId));
					}
					//保存原始数据
					sic.setOldText(textStory);
					sic.setColor(color);
					sic.setStrong(strong);
					sic.setEm(em);
					sic.setU(u);
					sic.setTextAlign(textAlign);
					sic.setFont(font);
					sic.setImgList(imgList);
					
				}
				sic.setPaper(paper);
				sic.setImgStoryUrl(TextHelper.isNullOrEmpty(imgStoryMap.get("realSrc"))?"":imgStoryMap.get("realSrc").toString());
				sic.setVoiceStoryUrl(TextHelper.isNullOrEmpty(voiceStoryMap.get("data"))?"":voiceStoryMap.get("data").toString());
				sic.setVideoStoryUrl(TextHelper.isNullOrEmpty(videoStoryMap.get("data"))?"":videoStoryMap.get("data").toString());
				sic.setImg(TextHelper.isNullOrEmpty(imgMap.get("realSrc"))?"":imgMap.get("realSrc").toString());
				//新增章节
				Long id = storyIslandService.createChapter(sic,si);
				if(id.longValue() == -1 ){
					data.setMsg("admin_forbidden");
					data.setStatus(FAIL);
				}else{
					if("1".equals(status)){
						StoryIsland story =  storyIslandService.getStoryIslandById(id.toString());
						//查询订阅改故事的人type=2  一次只能推送1000条
						List<String> list = subscibeService.getSubscibeMemberList(id);
						if(!TextHelper.isNullOrEmpty(story) && list !=null && list.size()>0){
							PushExample.testSendPushApp(list,"你订阅的故事"+story.getTitle()+"有更新了。","2");
						}
						
						
						//关注的用户也提醒
						List<String> attentionList = attentionService.getAttentionMember(memberId);
						Member member = memberService.getMemberById(memberId);
						
						if(!TextHelper.isNullOrEmpty(member) && attentionList !=null && attentionList.size()>0){
							if("1".equals(isAnonymous)){
								PushExample.testSendPushApp(attentionList,"你关注的用户"+name+"有更新了。","5");
							}else{
								PushExample.testSendPushApp(attentionList,"你关注的用户"+member.getName()+"有更新了。","5");
							}
						}
					}
					StoryIsland s = storyIslandService.getStoryIsland(id.toString(),"","");
					data.putInData("id",id);
					data.putInData("storyIsland",s);
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
	
	
	public static Map<String,String> getTextStory(String textStory,List<MultipartFile> fileList,String color,String strong,String em,String u,String textAlign,String font,String paper){
		Map<String,String> textMap = new HashMap<String,String>();
		textStory = textStory.replace(" ", "&nbsp;");
		String[]  textArray = textStory.split("<img>");
		String text = "<html><head><style type=\"text/css\">span {"+color+font+"} p { margin-left:5px;margin-right:5px;margin-top:0;margin-bottom:0;"+textAlign+"} img{width:100%;height:auto;} </style></head><body style=\"line-height:25px;\">";
		String textCenter = "";
		
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < textArray.length; i++) {
			if(!TextHelper.isNullOrEmpty(textArray[i])){
				list.add(textArray[i].trim());
			}
		}
		String imgList = "";
		for (int i = 0; i < list.size(); i++) {
			if("@".equals(list.get(i))){
				if(null!=fileList&&fileList.size()>0){
					Map<String,Object> map = FileController.uploadFile(fileList.get(0),"2");
					String src = map.get("realSrc").toString();
					textCenter += "<p style=\"margin-top:10px;margin-bottom:10px;\"><img src=\""+src+"\" /></p>";
					fileList.remove(0);
					imgList += src+",";
				}
			}else if("#".equals(list.get(i))){
				if(i>=1 && i < (list.size()-1)){
					if("#".equals(list.get(i-1))){
						//将前台页面的换行也展示出来。
						textCenter +="<br>";
					}
				}else{
					textCenter +="<br>";
				}
			}else{
				textCenter +="<p><strong><em><u><span>"+list.get(i)+"</span></u></em></strong></p>";
			}
		}
		if(TextHelper.isNullOrEmpty(strong)){
			textCenter = textCenter.replace("<strong>", "").replace("</strong>", "");
		}
		if(TextHelper.isNullOrEmpty(em)){
			textCenter = textCenter.replace("<em>", "").replace("</em>", "");
		}
		if(TextHelper.isNullOrEmpty(u)){
			textCenter = textCenter.replace("<u>", "").replace("</u>", "");
		}
		if(!TextHelper.isNullOrEmpty(color)){
			text = text.replace(color, "color:"+color+";");
		}
		if(!TextHelper.isNullOrEmpty(font)){
			text = text.replace(font, "font-family:"+font+";");
		}
		if(!TextHelper.isNullOrEmpty(textAlign)){
			text = text.replace(textAlign, "text-align:"+textAlign+";");
		}
		text = text.replace("null", "");
		String head="";
		String foot="";
		if(!TextHelper.isNullOrEmpty(paper)){
			head = "<div><div><img src=\""+BlueMobiConstant.domain+"imgs/"+paper+"_1.png\" /></div><div style=\"background-image:url("+BlueMobiConstant.domain+"imgs/"+paper+"_2.png); background-repeat:repeat-y;background-size:100%;min-height:500px;\" >";
			if("3".equals(paper)||"4".equals(paper)){
				head = "<div><div style=\"background-image:url("+BlueMobiConstant.domain+"imgs/"+paper+"_2.png); background-repeat:repeat-y;background-size:100%;min-height:400px;\" >";	
			}
			foot = "</div><div><img src=\""+BlueMobiConstant.domain+"imgs/"+paper+"_3.png\" /></div></div> ";
			textCenter = head +textCenter+foot;
		}
		text = text+textCenter+"</body></html>";
		if(!TextHelper.isNullOrEmpty(imgList)){
			imgList = imgList.substring(0, imgList.length()-1);
		}
		textMap.put("text", text);
		textMap.put("imgList", imgList);
		return textMap;
		
	}
	
	/**
	 * 获取图文故事数据接口
	 * 
	 */
	@RequestMapping(value = "app/getTextStory", method = RequestMethod.POST)
	public void getTextStory(String chapterId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(chapterId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//故事详情
				StoryIslandChapter s = storyIslandService.getTextStory(chapterId);
				data.setStatus(SUCCESS);
				data.putInData("storyIslandChapter",s);
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
	 * 用户的 电影 故事 剧本下拉列表  
	 */
	@RequestMapping(value = "app/getAllList", method = RequestMethod.POST)
	public void getAllList(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(memberId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				List<StoryIsland> list1 = storyIslandService.getStoryList(memberId);
			    list1 = list1==null ? new ArrayList<StoryIsland>():list1;
			    List<MicroFilm> list2 = microFilmService.getMicroFilmList(memberId);
			    list2= list2==null ? new ArrayList<MicroFilm>():list2;
			    List<ScriptFactory> list3 = scriptFactoryService.getScriptFactoryList(memberId);
			    list3 = list3==null ? new ArrayList<ScriptFactory>():list3;
			    Map<String,Object> map = new HashMap<String, Object>();
			    
			    map.put("storyIslandList", list1);
			    map.put("microFilmList", list2);
			    map.put("scriptFactoryList", list3);
			    
				data.putInData("myListMap",map);
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
	 * 关联
	 */
	@RequestMapping(value = "app/addRelate", method = RequestMethod.POST)
	public void addRelate(String storyIslandId,String microFilmId,String scriptFactoryId,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(type)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				relateService.addRelate(storyIslandId,microFilmId,scriptFactoryId,type);
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
	 * 删除章节
	 */
	@RequestMapping(value = "app/deleteStoryIslandChapter", method = RequestMethod.POST)
	public void deleteStoryIslandChapter(String chapterId ) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(chapterId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				StoryIslandChapter sic = storyIslandService.getChapterById(chapterId);
				storyIslandService.deleteStoryIslandChapter(chapterId,sic.getStoryIslandId().toString());
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
	 * 获取故事详情
	 */
	@RequestMapping(value = "app/getStoryChapterList", method = RequestMethod.POST)
	public void getChapterList(String storyId,String memberId,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(storyId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				//故事详情
				StoryIsland s = storyIslandService.getStoryIsland(storyId,memberId,type);
				//如果是订阅用户，则更新阅读时间
				if(!TextHelper.isNullOrEmpty(s)){
					if("1".equals(s.getIsSubscibe())){
						subscibeService.updateReadTime(storyId,memberId);
					}
				    //故事章节列表 1是文字 2是图片 3是音频 4是视频
					List<StoryIslandChapter> list = storyIslandService.getALLChapter(storyId,type);
					list = list==null ? new ArrayList<StoryIslandChapter>():list;
					//关联
					map.put("storyIsland", s);
					map.put("list", list);
					data.putInData("story",map);
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
	 * 调序
	 */
	@RequestMapping(value = "app/storySort", method = RequestMethod.POST)
	public void storySort(String ids) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(ids)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				String[] idArry = ids.split(",");
				for (int i = 0; i < idArry.length; i++) {
					String id = idArry[i];
					storyIslandService.updateSort(id,i+1);
				}
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
	 * 故事岛封面
	 */
	@RequestMapping(value = "app/coverList", method = RequestMethod.POST)
	public void coverList() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			List<Cover> list =  storyIslandService.coverList();
			list = list==null ? new ArrayList<Cover>():list;
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
	 * 章节修改
	 */
	@RequestMapping(value = "app/editStoryIslandChapter", method = RequestMethod.POST)
	public void editStoryIslandChapter(String chapterId,String status) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(status) || TextHelper.isNullOrEmpty(chapterId)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//将章节保存在实体里
				StoryIslandChapter sic = new StoryIslandChapter();
				sic.setId(Long.valueOf(chapterId));
				sic.setStatus(status);
				//保存章节
				storyIslandService.updateStoryIslandChapter(sic);
				StoryIslandChapter s = storyIslandService.getChapterById(chapterId);
				storyIslandService.updateUpdateTime(s.getStoryIslandId().toString(),status);
				if("1".equals(status)){
					StoryIsland story =  storyIslandService.getStoryIslandById(s.getStoryIslandId().toString());
					//查询订阅改故事的人type=2  一次只能推送1000条
					List<String> list = subscibeService.getSubscibeMemberList(s.getStoryIslandId());
					if(!TextHelper.isNullOrEmpty(story) && list !=null && list.size()>0){
						PushExample.testSendPushApp(list,"你订阅的故事"+story.getTitle()+"有更新了。","2");
					}
					
					
					//关注的用户也提醒
					List<String> attentionList = attentionService.getAttentionMember(story.getReleaseId().toString());
					Member member = memberService.getMemberById(story.getReleaseId().toString());
					if(!TextHelper.isNullOrEmpty(member) && attentionList !=null && attentionList.size()>0){
						PushExample.testSendPushApp(attentionList,"你关注的用户"+member.getName()+"有更新了。","5");
					}
				}
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
	 * 封面和标题修改
	 */
	@RequestMapping(value = "app/editStoryIsland", method = RequestMethod.POST)
	public void editStoryIsland(MultipartRequest re,String storyId,String cover1,String title,String classifyId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//上传封面文件
			MultipartFile coverFile = re.getFile("cover");
			Map<String,Object> coverMap = FileController.uploadFile(coverFile,"2");
			if(TextHelper.isNullOrEmpty(storyId.trim())){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//标题和封面就必须传进来了
				if(!TextHelper.isNullOrEmpty(cover1)){
					cover1 = cover1.trim();
				}
				String cover = TextHelper.isNullOrEmpty(coverMap.get("data"))?cover1:coverMap.get("data").toString();
				StoryIsland si = storyIslandService.getStoryIslandById(storyId.trim());
				if(!TextHelper.isNullOrEmpty(cover)){
					si.setCover(cover);
				}
				if(!TextHelper.isNullOrEmpty(title)){
					si.setTitle(title);
				}
				if(!TextHelper.isNullOrEmpty(classifyId)){
					si.setClassifyId(Integer.valueOf(classifyId));
				}
				storyIslandService.updateUpdateTime(si);
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
	 *发现--- 我的关注 --- 故事列表
	 * 
	 */
	@RequestMapping(value = "app/getAttentionStoryList", method = RequestMethod.POST)
	public void getAttentionStoryList(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Page page = new Page();
			page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
			page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
			page.setPageCount(-1);
			//查询互相关注的故事列表。
			List<StoryIslandChapter> list =  storyIslandService.getAttentionStoryList(memberId,page);
			list = list==null ? new ArrayList<StoryIslandChapter>():list;
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
	 * 分类点击统计
	 * 
	 */
	@RequestMapping(value = "app/addClassifyHit", method = RequestMethod.POST)
	public void addClassifyHit(String memberId,String classifyId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			storyIslandService.addClassifyHit(memberId,classifyId);
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
	 * 随机获取匿名头像和名字
	 * @return
	 */
	public static String getAnonymousNameAndIcon(){
		String[] array = new String[]{"Anne","Armstrong","Eva","Gavin","Ken","Leo","Selina"};
		int i = RandomUtils.nextInt(7);
		return array[i];
	}
	
}
