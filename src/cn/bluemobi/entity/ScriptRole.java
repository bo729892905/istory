package cn.bluemobi.entity; 
public class ScriptRole {
    private Long id;
    //角色名称
    private String name;
    //角色性别
    private String sex;
    //角色年龄段  0童年 1少年 2青年 3中年 4老年
    private String age;
    //角色
    private String role;
    //角色属性
    private String property;
    //描述
    private String description;
    //剧本id
    private String ScriptId;
    //用户id
    private String memberId;

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setAge(String age){
        this.age=age;
    }
    public String getAge(){
        return this.age;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String getRole(){
        return this.role;
    }
    public void setProperty(String property){
        this.property=property;
    }
    public String getProperty(){
        return this.property;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }
	public String getScriptId() {
		return ScriptId;
	}
	public void setScriptId(String scriptId) {
		ScriptId = scriptId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
