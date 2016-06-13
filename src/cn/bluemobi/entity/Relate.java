package cn.bluemobi.entity;

public class Relate {
	private Long id;
	private Long relateStoryId;
	private Long relateScriptId;
	private Long relateFilmId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRelateStoryId() {
		return relateStoryId;
	}
	public void setRelateStoryId(Long relateStoryId) {
		this.relateStoryId = relateStoryId;
	}
	public Long getRelateScriptId() {
		return relateScriptId;
	}
	public void setRelateScriptId(Long relateScriptId) {
		this.relateScriptId = relateScriptId;
	}
	public Long getRelateFilmId() {
		return relateFilmId;
	}
	public void setRelateFilmId(Long relateFilmId) {
		this.relateFilmId = relateFilmId;
	}
	
}
