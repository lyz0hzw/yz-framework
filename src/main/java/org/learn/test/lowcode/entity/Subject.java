package org.learn.test.lowcode.entity;

/**
 * @author Application
 */
public class Subject {
    
    private int subject_id;

    private String subject_name;

    private String subject_prefix;

    private String subject_remark;

    private int parent_id;

    private int dr;

    private String creator;

    private String modifier;

    public int getSubject_id(){
        return this.subject_id;
    }

    public String getSubject_name(){
        return this.subject_name;
    }

    public String getSubject_prefix(){
        return this.subject_prefix;
    }

    public String getSubject_remark(){
        return this.subject_remark;
    }

    public int getParent_id(){
        return this.parent_id;
    }

    public int getDr(){
        return this.dr;
    }

    public String getCreator(){
        return this.creator;
    }

    public String getModifier(){
        return this.modifier;
    }

    public void setSubject_id(int subject_id){
        this.subject_id=subject_id;
    }

    public void setSubject_name(String subject_name){
        this.subject_name=subject_name;
    }

    public void setSubject_prefix(String subject_prefix){
        this.subject_prefix=subject_prefix;
    }

    public void setSubject_remark(String subject_remark){
        this.subject_remark=subject_remark;
    }

    public void setParent_id(int parent_id){
        this.parent_id=parent_id;
    }

    public void setDr(int dr){
        this.dr=dr;
    }

    public void setCreator(String creator){
        this.creator=creator;
    }

    public void setModifier(String modifier){
        this.modifier=modifier;
    }

}