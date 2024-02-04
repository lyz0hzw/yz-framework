package org.learn.test.lowcode.entity;

/**
 * @author Application
 */
public class Corpus_conversation_single {
    
    private int id;

    private int type;

    private int subject;

    private String creator;

    private String create_time;

    private int state;

    private String update_time;

    private String modifier;

    private String prompt;

    private String response;

    private String chosen;

    private String rejected;

    private int dr;

    private String ytenant_id;

    private String ts;

    public int getId(){
        return this.id;
    }

    public int getType(){
        return this.type;
    }

    public int getSubject(){
        return this.subject;
    }

    public String getCreator(){
        return this.creator;
    }

    public String getCreate_time(){
        return this.create_time;
    }

    public int getState(){
        return this.state;
    }

    public String getUpdate_time(){
        return this.update_time;
    }

    public String getModifier(){
        return this.modifier;
    }

    public String getPrompt(){
        return this.prompt;
    }

    public String getResponse(){
        return this.response;
    }

    public String getChosen(){
        return this.chosen;
    }

    public String getRejected(){
        return this.rejected;
    }

    public int getDr(){
        return this.dr;
    }

    public String getYtenant_id(){
        return this.ytenant_id;
    }

    public String getTs(){
        return this.ts;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setType(int type){
        this.type=type;
    }

    public void setSubject(int subject){
        this.subject=subject;
    }

    public void setCreator(String creator){
        this.creator=creator;
    }

    public void setCreate_time(String create_time){
        this.create_time=create_time;
    }

    public void setState(int state){
        this.state=state;
    }

    public void setUpdate_time(String update_time){
        this.update_time=update_time;
    }

    public void setModifier(String modifier){
        this.modifier=modifier;
    }

    public void setPrompt(String prompt){
        this.prompt=prompt;
    }

    public void setResponse(String response){
        this.response=response;
    }

    public void setChosen(String chosen){
        this.chosen=chosen;
    }

    public void setRejected(String rejected){
        this.rejected=rejected;
    }

    public void setDr(int dr){
        this.dr=dr;
    }

    public void setYtenant_id(String ytenant_id){
        this.ytenant_id=ytenant_id;
    }

    public void setTs(String ts){
        this.ts=ts;
    }

}