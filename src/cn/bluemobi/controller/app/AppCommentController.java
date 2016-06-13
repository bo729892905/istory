package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.AppController;
import cn.bluemobi.controller.PushExample;
import cn.bluemobi.entity.Comment;
import cn.bluemobi.entity.FilterWords;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.Relate;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.StarStory;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.CommentService;
import cn.bluemobi.service.FilterWordsService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.RelateService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 评论接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppCommentController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FilterWordsService filterWordsService;
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private RelateService relateService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private StarStoryService starStoryService;
	/**
	 * 评论列表
	 * 
	 * 被评论类型 1是故事 2电影 3剧本 4星故事
	 */
	@RequestMapping(value = "app/getCommentList", method = RequestMethod.POST)
	public void getCommentList(String beCommentId,String secondId,String type,String memberId,String order) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(beCommentId)||TextHelper.isNullOrEmpty(type)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				Page page = new Page();
				page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
				page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
				page.setPageCount(-1);
				List<Comment> list = commentService.getCommentList(beCommentId,secondId,type,memberId,page,order);
				list = list==null ? new ArrayList<Comment>():list;
				List<FilterWords> filterWordsList = filterWordsService.getFilterList();
				filterWordsList = filterWordsList==null ? new ArrayList<FilterWords>():filterWordsList;
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < filterWordsList.size(); j++) {
						String filterWords = filterWordsList.get(j).getFilterWords();
						String replaceWords =""; 
						for (int k = 0; k < filterWords.length(); k++) {
							replaceWords += "*";
						}
						list.get(i).setComment(list.get(i).getComment().replace(filterWords, replaceWords));
					}
				}
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
	 * 发表评论接口         
	 * 
	 * 被评论类型 1是故事 2电影 3剧本 4星故事 5是评论
	 */
	@RequestMapping(value = "app/addComment", method = RequestMethod.POST)
	public void addComment(Comment comment) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(comment.getReleaseId())||TextHelper.isNullOrEmpty(comment.getType())||TextHelper.isNullOrEmpty(comment.getBeCommentId())||TextHelper.isNullOrEmpty(comment.getComment())){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//新增匿名头像和匿名名字
				if("1".equals(comment.getIsAnonymous())){
					String name = getAnonymousNameAndIcon();
					comment.setAnonymousName(name);
					comment.setAnonymousIcon(BlueMobiConstant.domain+"imgs/icon/"+name+".png");
				}
				Long coid = commentService.addComment(comment);
				Comment co = commentService.getCommentById(coid);
				List<FilterWords> filterWordsList = filterWordsService.getFilterList();
				filterWordsList = filterWordsList==null ? new ArrayList<FilterWords>():filterWordsList;
				for (int j = 0; j < filterWordsList.size(); j++) {
					String filterWords = filterWordsList.get(j).getFilterWords();
					String replaceWords =""; 
					for (int k = 0; k < filterWords.length(); k++) {
						replaceWords += "*";
					}
					co.setComment(co.getComment().replace(filterWords, replaceWords));
				}
				
				
				String type = co.getType();
				Long id = co.getBeCommentId();
				String receiveId="";
				if("1".equals(type)){
					StoryIsland si = storyIslandService.getStoryIslandById(id.toString());
					if(!TextHelper.isNullOrEmpty(si)){
						co.setCover(si.getCover());
						co.setTitle(si.getTitle());
						//获取故事的发布人id
						receiveId = co.getReleaseId().toString();
					}
					Relate r = relateService.getRelatebyStory(id.toString());
					if(!TextHelper.isNullOrEmpty(r)){
						co.setRelateFilmId(r.getRelateFilmId());
						co.setRelateScriptId(r.getRelateScriptId());
					}
				}else if("2".equals(type)){
					MicroFilm mf = microFilmService.getMicroFilmById(id.toString());
					if(!TextHelper.isNullOrEmpty(mf)){
						co.setCover(mf.getCover());
						co.setTitle(mf.getTitle());
						//获取故事的发布人id
						receiveId = mf.getReleaseId().toString();
					}
					Relate r = relateService.getRelatebyFilm(id.toString());		
					if(!TextHelper.isNullOrEmpty(r)){
						co.setRelateStoryId(r.getRelateStoryId());
						co.setRelateScriptId(r.getRelateScriptId());
					}
				}else if("3".equals(type)){
					ScriptFactory sf = scriptFactoryService.getScriptFactoryById(id.toString());
					if(!TextHelper.isNullOrEmpty(sf)){
						co.setCover(sf.getCover());
						co.setTitle(sf.getTitle());
						//获取故事的发布人id
						receiveId = sf.getReleaseId().toString();
					}
					Relate r = relateService.getRelateByScript(id.toString());	
					if(!TextHelper.isNullOrEmpty(r)){
						co.setRelateStoryId(r.getRelateStoryId());
						co.setRelateFilmId(r.getRelateFilmId());
					}
				}else if("4".equals(type)){
					StarStory ss  = starStoryService.getStarStoryById(id.toString());
					if(!TextHelper.isNullOrEmpty(ss)){
						co.setCover(ss.getCover());
						co.setTitle(ss.getTitle());
					}
				}
				
				co.setIsPraise("0");
				data.putInData("comment",co);
				data.setStatus(SUCCESS);
				if(!TextHelper.isNullOrEmpty(receiveId)){
					//推送评论
					PushExample.testSendPushApp(new String[]{"ID_"+receiveId}, "收到一条新的评论", "4");
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
	 * 评论我的
	 */
	@RequestMapping(value = "app/getMyComment", method = RequestMethod.POST)
	public void getMyComment(String memberId) {
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
				
				List<Comment> list =  commentService.getMyComment(memberId,page);
				
				list = list==null ? new ArrayList<Comment>():list;
				List<FilterWords> filterWordsList = filterWordsService.getFilterList();
				filterWordsList = filterWordsList==null ? new ArrayList<FilterWords>():filterWordsList;
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < filterWordsList.size(); j++) {
						String filterWords = filterWordsList.get(j).getFilterWords();
						String replaceWords =""; 
						for (int k = 0; k < filterWords.length(); k++) {
							replaceWords += "*";
						}
						list.get(i).setComment(list.get(i).getComment().replace(filterWords, replaceWords));
					}
					
					Long id = list.get(i).getBeCommentId();
					String type = list.get(i).getType();
					if("1".equals(type)){
						StoryIsland si = storyIslandService.getStoryIslandById(id.toString());
						if(!TextHelper.isNullOrEmpty(si)){
							list.get(i).setCover(si.getCover());
							list.get(i).setTitle(si.getTitle());
						}
						Relate r = relateService.getRelatebyStory(id.toString());
						if(!TextHelper.isNullOrEmpty(r)){
							list.get(i).setRelateFilmId(r.getRelateFilmId());
							list.get(i).setRelateScriptId(r.getRelateScriptId());
						}
					}else if("2".equals(type)){
						MicroFilm mf = microFilmService.getMicroFilmById(id.toString());
						if(!TextHelper.isNullOrEmpty(mf)){
							list.get(i).setCover(mf.getCover());
							list.get(i).setTitle(mf.getTitle());
						}
						Relate r = relateService.getRelatebyFilm(id.toString());		
						if(!TextHelper.isNullOrEmpty(r)){
							list.get(i).setRelateStoryId(r.getRelateStoryId());
							list.get(i).setRelateScriptId(r.getRelateScriptId());
						}
					}else if("3".equals(type)){
						ScriptFactory sf = scriptFactoryService.getScriptFactoryById(id.toString());
						if(!TextHelper.isNullOrEmpty(sf)){
							list.get(i).setCover(sf.getCover());
							list.get(i).setTitle(sf.getTitle());
						}
						Relate r = relateService.getRelateByScript(id.toString());	
						if(!TextHelper.isNullOrEmpty(r)){
							list.get(i).setRelateStoryId(r.getRelateStoryId());
							list.get(i).setRelateFilmId(r.getRelateFilmId());
						}
					}else if("4".equals(type)){
						StarStory ss  = starStoryService.getStarStoryById(id.toString());
						if(!TextHelper.isNullOrEmpty(ss)){
							list.get(i).setCover(ss.getCover());
							list.get(i).setTitle(ss.getTitle());
						}
					}
					
				}
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
	 * 随机获取匿名头像和名字
	 * @return
	 */
	public static String getAnonymousNameAndIcon(){
		String[] array = new String[]{"Anne","Armstrong","Eva","Gavin","Ken","Leo","Selina"};
		int i = RandomUtils.nextInt(7);
		return array[i];
	}
	
}
