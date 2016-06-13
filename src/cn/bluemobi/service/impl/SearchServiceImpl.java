package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.SearchService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 发现接口
 * @author xiazf
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private MemberService memberService;
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	/**
	 *查找所有
	 */
	public Map<String, Object> getAllBySearch(String memberId, String keywords,String type,Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		//查找用户
		if(TextHelper.isNullOrEmpty(type) || "0".equals(type)){
			List<Member> memberList = memberService.getAllMemberListBySearch(memberId,keywords,page);
			map.put("memberList", memberList==null?new ArrayList<Member>():memberList);
		}
		//查找故事
		if(TextHelper.isNullOrEmpty(type) || "1".equals(type)){
			List<StoryIsland> storyIslandList = storyIslandService.getStoryIslandListBySearch(keywords,page);
			map.put("storyIslandList", storyIslandList==null?new ArrayList<StoryIsland>():storyIslandList);
		}
		//查找电影
		if(TextHelper.isNullOrEmpty(type) || "2".equals(type)){
			List<MicroFilm> microFilmList = microFilmService.getMicroFilmListBySearch(keywords,page);
			map.put("microFilmList", microFilmList==null?new ArrayList<MicroFilm>():microFilmList);
		}
		//查找剧本
		if(TextHelper.isNullOrEmpty(type) || "3".equals(type)){
			List<ScriptFactory> scriptFactoryList = scriptFactoryService.getScriptFactoryBySearch(keywords,page);
			map.put("scriptFactoryList", scriptFactoryList==null?new ArrayList<ScriptFactory>():scriptFactoryList);
		}
		return map;
	}

}
