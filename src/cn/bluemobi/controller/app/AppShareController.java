package cn.bluemobi.controller.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.Comment;
import cn.bluemobi.entity.FilterWords;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.ScriptChapter;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.StarStory;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.StoryIslandChapter;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.CommentService;
import cn.bluemobi.service.FilterWordsService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 分享
 * @author xiazf
 *
 */
@Controller
public class AppShareController extends AppController {
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FilterWordsService filterWordsService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private StarStoryService starStoryService;
	/**
	 * app分享
	 *
	 */
	@RequestMapping("app/webShare")
	public String webShare(String id,String chapterId,String type ) {
		if("1".equals(type)){//故事
			StoryIsland s = storyIslandService.getStoryIsland(id,"","");
			//故事章节列表 1是文字 2是图片 3是音频 4是视频
			List<StoryIslandChapter> list  = storyIslandService.getALLChapter(id, "1");
			if(!TextHelper.isNullOrEmpty(s.getRelateFilmId())){
				MicroFilm m = microFilmService.getMicroFilmById(s.getRelateFilmId());
				request.setAttribute("microFilm", m);
				List<Comment> list1 =  getCommentList(m.getId().toString(),"","2","","0");
				request.setAttribute("commentList1", list1);
			}
			if(!TextHelper.isNullOrEmpty(chapterId)){
				chapterId = "";
			}

			List<Comment> commentlist =  getCommentList(id,chapterId,type,"","0");
			request.setAttribute("commentList", commentlist);

			request.setAttribute("storyIsland", s);
			request.setAttribute("list", list);

		}else if("2".equals(type)){//微电影
			MicroFilm m = microFilmService.getMicroFilmById(id);
			if(!TextHelper.isNullOrEmpty(m.getRelateStoryId())){
				StoryIsland s = storyIslandService.getStoryIsland(m.getRelateStoryId().toString(),"","");
				//故事章节列表 1是文字 2是图片 3是音频 4是视频
				List<StoryIslandChapter> list  = storyIslandService.getALLChapter(s.getId().toString(), "1");
				List<Comment> list1 =  getCommentList(s.getId().toString(),"","1","","0");
				request.setAttribute("storyIsland", s);
				request.setAttribute("list", list);
				request.setAttribute("commentList", list1);
			}
			List<Comment> commentlist =  getCommentList(id,chapterId,type,"","0");

			Page page = new Page();
			try {
				page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
				page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "6": getParameter("pageSize")));
				page.setPageCount(-1);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<StoryIsland> choicenessList = storyIslandService.getStoryIslandList("","1","",type,page);
			request.setAttribute("choicenessList", choicenessList);


			request.setAttribute("commentList1", commentlist);
			request.setAttribute("microFilm", m);
		}else if("3".equals(type)){//剧本
			ScriptChapter script = scriptFactoryService.getChapterById(chapterId);
			ScriptFactory ssss= scriptFactoryService.getScriptFactoryById(id);
			List<Comment> commentlist =  getCommentList(id,chapterId,type,"","0");
			request.setAttribute("commentList1", commentlist);
			request.setAttribute("title",ssss.getTitle() );
			request.setAttribute("scriptChapter", script);
		}else if("4".equals(type)){//星故事
			StarStory ss = starStoryService.getStarStoryById(id);
			String adn = "";
			if(!TextHelper.isNullOrEmpty(ss) && !TextHelper.isNullOrEmpty(ss.getContent())){
				adn = ss.getContent().replace("\r","<br>");
				ss.setContent(adn);
			}
			request.setAttribute("starStory", ss);
			List<Comment> commentlist =  getCommentList(id,chapterId,type,"","0");
			request.setAttribute("commentList1", commentlist);
		}
		request.setAttribute("type", type);
		return "/admin/web_app.jsp";
	}


	/**
	 * 查找评论
	 * @param beCommentId
	 * @param secondId
	 * @param type
	 * @param memberId
	 * @param order
	 * @return
	 */
	public List<Comment> getCommentList(String beCommentId,String secondId,String type,String memberId,String order) {
		try {
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
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 用户协议
	 */
	@RequestMapping("app/protocol")
	public String protocol() {
		return "/admin/protocol.jsp";
	}

	/**
	 * 进入下载页面
	 *
	 */
	@RequestMapping(value = "app/webDownload", method = RequestMethod.GET)
	public String webShare(){
		return "admin/web_app_download.jsp";
	}

}
