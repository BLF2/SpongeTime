package cn.updev.Users.Static;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登录判断
 * 获得登录用户
 * Created by hypo on 15-9-29.
 */
public class Login {
    private IUser user;
    private String passWord;

    public Login(){

    }

    public Login(String email, String passWord) {

        //判断合法E-Mail
        Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()){

            // email是用户注册邮箱
            user = null;
        }else{

            // 邮箱非法，不用再去尝试获取用户
            user = null;
        }

        this.passWord = passWord;

        try {
            //密码MD5加密 把注册邮箱作为盐
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((passWord + user.geteMail()).getBytes());
            passWord = new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Boolean judge(){

        if(user == null){
            return false;
        }

        if(passWord.equals(user.getPassWord())){
            setSession();
            return true;
        }

        return false;
    }

    private void setSession(){

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        session.setAttribute("LoingedUser", user);

    }

    public IUser getLoginedUser(){

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        IUser rnt = (IUser) session.getAttribute("LoingedUser");

        return rnt;
    }
}
