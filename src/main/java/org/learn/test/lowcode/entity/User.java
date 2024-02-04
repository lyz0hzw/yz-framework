package org.learn.test.lowcode.entity;

/**
 * @author Application
 */
public class User {
    
    private int id;

    private String user_name;

    private String password;

    private String role;

    private String name;

    private String email;

    private String Host;

    private String User;

    private String Select_priv;

    private String Insert_priv;

    private String Update_priv;

    private String Delete_priv;

    private String Create_priv;

    private String Drop_priv;

    private String Reload_priv;

    private String Shutdown_priv;

    private String Process_priv;

    private String File_priv;

    private String Grant_priv;

    private String References_priv;

    private String Index_priv;

    private String Alter_priv;

    private String Show_db_priv;

    private String Super_priv;

    private String Create_tmp_table_priv;

    private String Lock_tables_priv;

    private String Execute_priv;

    private String Repl_slave_priv;

    private String Repl_client_priv;

    private String Create_view_priv;

    private String Show_view_priv;

    private String Create_routine_priv;

    private String Alter_routine_priv;

    private String Create_user_priv;

    private String Event_priv;

    private String Trigger_priv;

    private String Create_tablespace_priv;

    private String ssl_type;

    private String ssl_cipher;

    private String x509_issuer;

    private String x509_subject;

    private int max_questions;

    private int max_updates;

    private int max_connections;

    private int max_user_connections;

    private String plugin;

    private String authentication_string;

    private String password_expired;

    private String password_last_changed;

    private int password_lifetime;

    private String account_locked;

    private String Create_role_priv;

    private String Drop_role_priv;

    private int Password_reuse_history;

    private int Password_reuse_time;

    private String Password_require_current;

    private String User_attributes;

    public int getId(){
        return this.id;
    }

    public String getUser_name(){
        return this.user_name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRole(){
        return this.role;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getHost(){
        return this.Host;
    }

    public String getUser(){
        return this.User;
    }

    public String getSelect_priv(){
        return this.Select_priv;
    }

    public String getInsert_priv(){
        return this.Insert_priv;
    }

    public String getUpdate_priv(){
        return this.Update_priv;
    }

    public String getDelete_priv(){
        return this.Delete_priv;
    }

    public String getCreate_priv(){
        return this.Create_priv;
    }

    public String getDrop_priv(){
        return this.Drop_priv;
    }

    public String getReload_priv(){
        return this.Reload_priv;
    }

    public String getShutdown_priv(){
        return this.Shutdown_priv;
    }

    public String getProcess_priv(){
        return this.Process_priv;
    }

    public String getFile_priv(){
        return this.File_priv;
    }

    public String getGrant_priv(){
        return this.Grant_priv;
    }

    public String getReferences_priv(){
        return this.References_priv;
    }

    public String getIndex_priv(){
        return this.Index_priv;
    }

    public String getAlter_priv(){
        return this.Alter_priv;
    }

    public String getShow_db_priv(){
        return this.Show_db_priv;
    }

    public String getSuper_priv(){
        return this.Super_priv;
    }

    public String getCreate_tmp_table_priv(){
        return this.Create_tmp_table_priv;
    }

    public String getLock_tables_priv(){
        return this.Lock_tables_priv;
    }

    public String getExecute_priv(){
        return this.Execute_priv;
    }

    public String getRepl_slave_priv(){
        return this.Repl_slave_priv;
    }

    public String getRepl_client_priv(){
        return this.Repl_client_priv;
    }

    public String getCreate_view_priv(){
        return this.Create_view_priv;
    }

    public String getShow_view_priv(){
        return this.Show_view_priv;
    }

    public String getCreate_routine_priv(){
        return this.Create_routine_priv;
    }

    public String getAlter_routine_priv(){
        return this.Alter_routine_priv;
    }

    public String getCreate_user_priv(){
        return this.Create_user_priv;
    }

    public String getEvent_priv(){
        return this.Event_priv;
    }

    public String getTrigger_priv(){
        return this.Trigger_priv;
    }

    public String getCreate_tablespace_priv(){
        return this.Create_tablespace_priv;
    }

    public String getSsl_type(){
        return this.ssl_type;
    }

    public String getSsl_cipher(){
        return this.ssl_cipher;
    }

    public String getX509_issuer(){
        return this.x509_issuer;
    }

    public String getX509_subject(){
        return this.x509_subject;
    }

    public int getMax_questions(){
        return this.max_questions;
    }

    public int getMax_updates(){
        return this.max_updates;
    }

    public int getMax_connections(){
        return this.max_connections;
    }

    public int getMax_user_connections(){
        return this.max_user_connections;
    }

    public String getPlugin(){
        return this.plugin;
    }

    public String getAuthentication_string(){
        return this.authentication_string;
    }

    public String getPassword_expired(){
        return this.password_expired;
    }

    public String getPassword_last_changed(){
        return this.password_last_changed;
    }

    public int getPassword_lifetime(){
        return this.password_lifetime;
    }

    public String getAccount_locked(){
        return this.account_locked;
    }

    public String getCreate_role_priv(){
        return this.Create_role_priv;
    }

    public String getDrop_role_priv(){
        return this.Drop_role_priv;
    }

    public int getPassword_reuse_history(){
        return this.Password_reuse_history;
    }

    public int getPassword_reuse_time(){
        return this.Password_reuse_time;
    }

    public String getPassword_require_current(){
        return this.Password_require_current;
    }

    public String getUser_attributes(){
        return this.User_attributes;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setUser_name(String user_name){
        this.user_name=user_name;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setRole(String role){
        this.role=role;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setHost(String Host){
        this.Host=Host;
    }

    public void setUser(String User){
        this.User=User;
    }

    public void setSelect_priv(String Select_priv){
        this.Select_priv=Select_priv;
    }

    public void setInsert_priv(String Insert_priv){
        this.Insert_priv=Insert_priv;
    }

    public void setUpdate_priv(String Update_priv){
        this.Update_priv=Update_priv;
    }

    public void setDelete_priv(String Delete_priv){
        this.Delete_priv=Delete_priv;
    }

    public void setCreate_priv(String Create_priv){
        this.Create_priv=Create_priv;
    }

    public void setDrop_priv(String Drop_priv){
        this.Drop_priv=Drop_priv;
    }

    public void setReload_priv(String Reload_priv){
        this.Reload_priv=Reload_priv;
    }

    public void setShutdown_priv(String Shutdown_priv){
        this.Shutdown_priv=Shutdown_priv;
    }

    public void setProcess_priv(String Process_priv){
        this.Process_priv=Process_priv;
    }

    public void setFile_priv(String File_priv){
        this.File_priv=File_priv;
    }

    public void setGrant_priv(String Grant_priv){
        this.Grant_priv=Grant_priv;
    }

    public void setReferences_priv(String References_priv){
        this.References_priv=References_priv;
    }

    public void setIndex_priv(String Index_priv){
        this.Index_priv=Index_priv;
    }

    public void setAlter_priv(String Alter_priv){
        this.Alter_priv=Alter_priv;
    }

    public void setShow_db_priv(String Show_db_priv){
        this.Show_db_priv=Show_db_priv;
    }

    public void setSuper_priv(String Super_priv){
        this.Super_priv=Super_priv;
    }

    public void setCreate_tmp_table_priv(String Create_tmp_table_priv){
        this.Create_tmp_table_priv=Create_tmp_table_priv;
    }

    public void setLock_tables_priv(String Lock_tables_priv){
        this.Lock_tables_priv=Lock_tables_priv;
    }

    public void setExecute_priv(String Execute_priv){
        this.Execute_priv=Execute_priv;
    }

    public void setRepl_slave_priv(String Repl_slave_priv){
        this.Repl_slave_priv=Repl_slave_priv;
    }

    public void setRepl_client_priv(String Repl_client_priv){
        this.Repl_client_priv=Repl_client_priv;
    }

    public void setCreate_view_priv(String Create_view_priv){
        this.Create_view_priv=Create_view_priv;
    }

    public void setShow_view_priv(String Show_view_priv){
        this.Show_view_priv=Show_view_priv;
    }

    public void setCreate_routine_priv(String Create_routine_priv){
        this.Create_routine_priv=Create_routine_priv;
    }

    public void setAlter_routine_priv(String Alter_routine_priv){
        this.Alter_routine_priv=Alter_routine_priv;
    }

    public void setCreate_user_priv(String Create_user_priv){
        this.Create_user_priv=Create_user_priv;
    }

    public void setEvent_priv(String Event_priv){
        this.Event_priv=Event_priv;
    }

    public void setTrigger_priv(String Trigger_priv){
        this.Trigger_priv=Trigger_priv;
    }

    public void setCreate_tablespace_priv(String Create_tablespace_priv){
        this.Create_tablespace_priv=Create_tablespace_priv;
    }

    public void setSsl_type(String ssl_type){
        this.ssl_type=ssl_type;
    }

    public void setSsl_cipher(String ssl_cipher){
        this.ssl_cipher=ssl_cipher;
    }

    public void setX509_issuer(String x509_issuer){
        this.x509_issuer=x509_issuer;
    }

    public void setX509_subject(String x509_subject){
        this.x509_subject=x509_subject;
    }

    public void setMax_questions(int max_questions){
        this.max_questions=max_questions;
    }

    public void setMax_updates(int max_updates){
        this.max_updates=max_updates;
    }

    public void setMax_connections(int max_connections){
        this.max_connections=max_connections;
    }

    public void setMax_user_connections(int max_user_connections){
        this.max_user_connections=max_user_connections;
    }

    public void setPlugin(String plugin){
        this.plugin=plugin;
    }

    public void setAuthentication_string(String authentication_string){
        this.authentication_string=authentication_string;
    }

    public void setPassword_expired(String password_expired){
        this.password_expired=password_expired;
    }

    public void setPassword_last_changed(String password_last_changed){
        this.password_last_changed=password_last_changed;
    }

    public void setPassword_lifetime(int password_lifetime){
        this.password_lifetime=password_lifetime;
    }

    public void setAccount_locked(String account_locked){
        this.account_locked=account_locked;
    }

    public void setCreate_role_priv(String Create_role_priv){
        this.Create_role_priv=Create_role_priv;
    }

    public void setDrop_role_priv(String Drop_role_priv){
        this.Drop_role_priv=Drop_role_priv;
    }

    public void setPassword_reuse_history(int Password_reuse_history){
        this.Password_reuse_history=Password_reuse_history;
    }

    public void setPassword_reuse_time(int Password_reuse_time){
        this.Password_reuse_time=Password_reuse_time;
    }

    public void setPassword_require_current(String Password_require_current){
        this.Password_require_current=Password_require_current;
    }

    public void setUser_attributes(String User_attributes){
        this.User_attributes=User_attributes;
    }

}