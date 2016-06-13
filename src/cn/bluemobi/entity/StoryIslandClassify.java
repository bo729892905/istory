package cn.bluemobi.entity;

import java.util.List;

/**
 * 故事岛分类
 * @author xiazf
 *
 */
public class StoryIslandClassify {
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 分类名
	 */
	private String classify;
	
	/**
	 * 故事岛列表
	 * 
	 */
	private List<StoryIsland> storyIslandList;
	/**
	 * 排序
	 * @return
	 */
	private Integer number;
	
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public List<StoryIsland> getStoryIslandList() {
		return storyIslandList;
	}
	public void setStoryIslandList(List<StoryIsland> storyIslandList) {
		this.storyIslandList = storyIslandList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	
	
}
