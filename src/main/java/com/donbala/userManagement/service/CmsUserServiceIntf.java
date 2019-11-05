package com.donbala.userManagement.service;

import com.donbala.menuManagement.model.CmsMenu;
import com.donbala.userManagement.model.CmsUser;

import java.util.List;
import java.util.Map;

public interface CmsUserServiceIntf {

     CmsUser getUserByUsercode(Map<String, String> usermap);

     void saveLoginTrace(String usercode, String logintype);

     List<CmsMenu> getUserMenu(String usercode);

     List<CmsMenu> getAllMenu();

     List<CmsUser> queryUsers(CmsUser cmsUser);

     void deleteUser(String usercode);

     CmsUser queryUserbyUsercode(String usercode);

     void saveUser(CmsUser cmsUser);

     void editUser(CmsUser cmsUser);

     void setPassword(CmsUser cmsUser);
}
