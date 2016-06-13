package cn.bluemobi.entity;

public class StoryHitSort {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Long memberId;
	/**
	 * 分类id
	 */
	private Long classifyId;
	/**
	 * 点击数
	 */
	private Long hit;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
}
