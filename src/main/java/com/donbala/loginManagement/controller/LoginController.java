package com.donbala.loginManagement.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.donbala.menuManagement.model.CmsMenu;
import com.donbala.userManagement.model.CmsUser;
import com.donbala.userManagement.service.CmsUserServiceIntf;
import com.donbala.util.CacheManager;
import com.donbala.util.DateUtil;
import com.donbala.util.MessageNotice;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {

    public final static Logger log = (Logger) LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CmsUserServiceIntf cmsUserServiceIntf;
    @Autowired
    private CacheManager cache;

    @PostMapping("/api/login/account11")
    @ResponseBody
    public Map<String, Object> account(@RequestBody User user){
        Map<String, Object> rMap = new HashMap<>();

        if(user==null){
            rMap.put("status","error");
            return rMap;
        }

        rMap.put("type", user.getType());

        if(user.getUserName().equals("admin")&& user.getPassword().equals("123456")){
            rMap.put("status","ok");
            rMap.put("name",user.getUserName());
            rMap.put("currentAuthority","admin2");
            return rMap;
        }
        if(user.getUserName().equals("user")&& user.getPassword().equals("123456")){
            rMap.put("status","ok");
            rMap.put("name",user.getUserName());
            rMap.put("currentAuthority","user1");
            return rMap;
        }

        rMap.put("status","error");
        rMap.put("errMsg", "用户不存在");

        return rMap;
    }

    public static class User{
        private String userName;
        private String password;
        private String type;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @PostMapping("/api/login/account")
    @ResponseBody
    @Transactional
    public Map<String, Object> userLogin(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        if(user==null){
            map.put("status","error");
            map.put("code", "401");
            map.put("msg", "用户名或密码不可为空");
            return map;
        }
        Map<String, String> usermap = new HashMap<>();
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        usermap.put("usercode",user.getUserName());
        usermap.put("password",password);
        CmsUser cmsUser = cmsUserServiceIntf.getUserByUsercode(usermap);

        if (cmsUser == null) {
            map.put("status", "error");
            map.put("code", "401");
            map.put("msg", "用户名或密码错误");
        }
        else {
            cmsUserServiceIntf.saveLoginTrace(user.getUserName(),"1");


            map.put("status", "ok");
            map.put("code", "200");

            map.put("userCode", cmsUser.getUsercode());
            map.put("userName", cmsUser.getName());
            //生成tokenId
            String token = UUID.randomUUID().toString().replaceAll("-","");
            String date = DateUtil.getSysDate();
            //向后端内存中放入token和用户信息--验证token用和根据token获取用户信息用
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put(token, date);
            tokenMap.put("user", cmsUser);
            cache.addCache(token, tokenMap);
            cache.addCache(cmsUser.getUsercode(), token);

            map.put("token", token);



        }

        return map;
    }


    /**
     * @description: 用户权限验证，成功后，则写入登录轨迹，返回用户所有菜单，失败则返回登录界面
     * session方式的
     *
    */
    @PostMapping("/userlogin11111")
    @ResponseBody
    public Map<String, Object> login(String usercode, String password, ModelAndView mv, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        MessageNotice messageNotice = new MessageNotice();
        Map<String, String> usermap = new HashMap<>();
        CmsUser cmsUser = new CmsUser();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        usermap.put("usercode",usercode);
        usermap.put("password",password);
        cmsUser = cmsUserServiceIntf.getUserByUsercode(usermap);

        //login failed
        if (cmsUser == null) {
            mv.setViewName("redirect:/");
            messageNotice.setFlag("0|");
            messageNotice.setMessage("用户名或密码错误");
        }
        else {
            cmsUserServiceIntf.saveLoginTrace(usercode,"1");

            List<CmsMenu> menus = cmsUserServiceIntf.getUserMenu(usercode);
            session.setAttribute("user",cmsUser);
            session.setAttribute("menus",menus);
            messageNotice.setFlag("1");

            map.put("flag", "1");
            map.put("user", cmsUser);
            map.put("menus", menus);
        }
        return map;
    }


    @PostMapping("/userlogin")
    @ResponseBody
    @Transactional
    public Map<String, Object> loginByToken(String usercode, String password) {
        Map<String, Object> map = new HashMap<>();

        Map<String, String> usermap = new HashMap<>();
        CmsUser cmsUser = new CmsUser();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        usermap.put("usercode",usercode);
        usermap.put("password",password);
        cmsUser = cmsUserServiceIntf.getUserByUsercode(usermap);

        if (cmsUser == null) {
            map.put("status", "fail");
            map.put("code", "401");
            map.put("msg", "用户名或密码错误");
        }
        else {
            cmsUserServiceIntf.saveLoginTrace(usercode,"1");
            //查询该用户权限下的菜单列表
            List<CmsMenu> menus = cmsUserServiceIntf.getUserMenu(usercode);

            map.put("status", "success");
            map.put("code", "200");
            //菜单列表
            map.put("menus", menus);
            map.put("userCode", cmsUser.getUsercode());
            map.put("name", cmsUser.getName());
            //生成tokenId
            String token = UUID.randomUUID().toString().replaceAll("-","");
            String date = DateUtil.getSysDate();
            //向后端内存中放入token和用户信息--验证token用和根据token获取用户信息用
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put(token, date);
            tokenMap.put("user", cmsUser);
            cache.addCache(token, tokenMap);
            cache.addCache(cmsUser.getUsercode(), token);

            map.put("token", token);



        }

        return map;
    }

    @PostMapping("/controller/antd/getUserMenu")
    @ResponseBody
    public Map<String, Object> getAntdUserMenu(@RequestBody String token){
        token = JSONObject.parseObject(token).getString("token");
        System.out.println("请求菜单的token："+token);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> tokenMap = (Map<String, Object>) cache.getValue(token);
        CmsUser cmsUser = (CmsUser) tokenMap.get("user");

        List<Map<String, Object>> menus = cmsUserServiceIntf.getAntdUserMenuByCode(cmsUser.getUsercode());
        map.put("code","200");
        map.put("menus", menus);

        return map;
    }

    @PostMapping("/controller/getUserMenu")
    @ResponseBody
    public Map<String, Object> getUserMenu(String token){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> tokenMap = (Map<String, Object>) cache.getValue(token);
        CmsUser cmsUser = (CmsUser) tokenMap.get("user");

        List<CmsMenu> menus = cmsUserServiceIntf.getUserMenu(cmsUser.getUsercode());
        map.put("code","200");
        map.put("menus", menus);

        return map;
    }

    /**
    *@description: 登出操作
    *@param: [mv, session]
    *@return: org.springframework.web.servlet.ModelAndView
    *@date: 2019/6/26 18:17
    *@author: wangran
    */
    @GetMapping("/userlogout11")
    public ModelAndView logout1(ModelAndView mv, HttpSession session){

        CmsUser cmsUser = (CmsUser) session.getAttribute("user");
        if (session == null || cmsUser == null) {
            mv.setViewName("redirect:/");
        }
        else if(session!=null && cmsUser!=null){
            session.invalidate();
            cmsUserServiceIntf.saveLoginTrace(cmsUser.getUsercode(),"0");
            mv.setViewName("redirect:/");
        }
        return mv;
    }
    /**
     *
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/29 9:22
     * @param token 1
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/userlogout")
    public void logout(String token){

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> tokenMap = (Map<String, Object>) cache.getValue(token);
        CmsUser cmsUser = (CmsUser) tokenMap.get("user");
        if(cmsUser!=null){
            cache.deleteCache(token);
            cache.deleteCache(cmsUser.getUsercode());
            cmsUserServiceIntf.saveLoginTrace(cmsUser.getUsercode(),"0");

        }
    }

}
