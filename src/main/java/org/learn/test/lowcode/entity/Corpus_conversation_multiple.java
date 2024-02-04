package org.learn.test.lowcode.entity;

/**
 * @author Application
 */
public class Corpus_conversation_multiple {
    
    private int id;

    private int corpus_id;

    private int sequence;

    private String update_time;

    private String modifier;

    private String prompt;

    private String response;

    private String chosen;

    private String rejected;

    public int getId(){
        return this.id;
    }

    public int getCorpus_id(){
        return this.corpus_id;
    }

    public int getSequence(){
        return this.sequence;
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

    public void setId(int id){
        this.id=id;
    }

    public void setCorpus_id(int corpus_id){
        this.corpus_id=corpus_id;
    }

    public void setSequence(int sequence){
        this.sequence=sequence;
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

}