package cn.bluemobi.entity; 
public class FilterWords {
    private Long id;
    private String filterWords;
    private Integer number;
    
    
    public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setFilterWords(String filterWords){
        this.filterWords=filterWords;
    }
    public String getFilterWords(){
        return this.filterWords;
    }
}
