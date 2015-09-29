package cn.updev.Db.Login;

import cn.updev.Users.Static.IUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hypo on 15-9-29.
 */
public class Login {
    private IUser user;
    private String passWord;

    public Login(String loginName, String passWord) {

        //loginName 可能是邮箱或者用户名， 分别尝试获得user对象
        user = null;

        this.passWord = passWord;
    }

    private void MD5(){
        //密码MD5加密 把注册邮箱作为盐
        try {
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
        MD5();
        if(passWord.equals(user.getPassWord())){
            return true;
        }

        return false;
    }
}
