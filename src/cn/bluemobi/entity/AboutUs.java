package cn.bluemobi.entity; 
public class AboutUs {
    private Long id;
    private String aboutUs;


    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setAboutUs(String aboutUs){
        this.aboutUs=aboutUs;
    }
    public String getAboutUs(){
        return this.aboutUs;
    }
}
