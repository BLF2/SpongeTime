package cn.updev.Action.Admin;

import cn.updev.Users.Static.FuctionClass.Login;
import cn.updev.Users.Static.FuctionClass.Register;
import cn.updev.Users.Static.UserOrGroupDAO.UserOrGroupQuery;
import cn.updev.Users.Static.UserOrGroupInterface.IUser;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hypo on 15-11-28.
 */
public class RegisterManager extends ActionSupport {
    private Map json;
    private String email;
    private String password;
    private String remember;
    private String userName;
    private String nickName;
    private String rePassword;
    private String url;

    private String code;

    public String execute() throws Exception {
        Login login = new Login();
        if(!login.isNotLogined()){
            return SUCCESS;
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        String error;

        if(this.userName == null || this.nickName == null || this.email == null || this.password == null){
            return INPUT;
        }

        if(!this.password.equals(this.rePassword)){
            error = "您两次填写的密码不一致！";
            request.setAttribute("error", error);
            return INPUT;
        }

        if(this.code == null || !this.code.equals("ayanami")){
            error = "内测码错误！请联系hhHypo：i@ihypo.net。";
            request.setAttribute("error", error);
            return INPUT;
        }

        Register register = new Register(this.userName, this.nickName, this.email, this.password ,this.url);

        IUser user = register.saveUserInfo();
        login.setUser(user);

        return SUCCESS;
    }

    public String judgeMailName(){
        Map<String, Object> map = new HashMap<String, Object>();

        if(userName != null){
            if(new UserOrGroupQuery().queryUserByName(userName) == null){
                map.put("userName", true);
            }else{
                map.put("userName", false);
            }
        }
        if(email != null){
            Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
            Matcher matcher = pattern.matcher(email);

            if(!matcher.matches()){
                map.put("email", false);
                map.put("message", "邮箱格式不合法！");
            }else{
                if(new UserOrGroupQuery().queryUserByEMail(email) == null){
                    map.put("email", true);
                }else{
                    map.put("email", false);
                    map.put("message", "该邮箱已被注册！");
                }
            }
        }
        this.setJson(map);
        return SUCCESS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Map getJson() {
        return json;
    }

    public void setJson(Map json) {
        this.json = json;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}