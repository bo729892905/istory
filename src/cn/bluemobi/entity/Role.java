package cn.bluemobi.entity; 
public class Role {
    private Long id;
    private String roleName;
    private String description;


    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }
    public String getRoleName(){
        return this.roleName;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }
}
