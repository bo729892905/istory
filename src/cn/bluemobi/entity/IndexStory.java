package cn.bluemobi.entity;
/**
 * 首页新故事列表
 * @author xiazf
 *
 */
public class IndexStory {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 类型 1是故事 2是电影3是剧本
	 */
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
