package com.donbala.menuManagement.controller;

import ch.qos.logback.classic.Logger;
import com.donbala.menuManagement.model.CmsMenu;
import com.donbala.userManagement.service.CmsUserServiceIntf;
import com.donbala.util.TreeNode;
import com.donbala.util.TreeNodeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @CLassName: MenuController
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/7/4-13:27
 * @Description: todo
 **/
@Controller
public class MenuController {

    public final static Logger log = (Logger)LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private CmsUserServiceIntf cmsUserServiceIntf;

    @GetMapping("/controller/menutree")
    @ResponseBody
    public List<TreeNode>  getMenuTree() {
        TreeNodeUtil treeNodeUtil = new TreeNodeUtil();
        List<CmsMenu> menulist = cmsUserServiceIntf.getAllMenu();
        List<TreeNode> treeNodes = treeNodeUtil.getNodeTree(menulist);
        return treeNodes;
    }

    @PostMapping("/menu/getMenuData")
    @ResponseBody
    public Set<Map<String, Object>> getMenuToAntdp(){
        System.out.println("进入menu");
        Set<Map<String, Object>> returnList = new LinkedHashSet<>();
        Map<String, Object> mapP = new HashMap<>();
        mapP.put("path","/dashboard");
        mapP.put("name","dashboard");
        mapP.put("icon","home");
//        mapP.put("authority","admin2");
        Set<Map<String, Object>> childrenList = new LinkedHashSet<>();

        Map<String, Object> mapC1 = new HashMap<>();
        mapC1.put("path", "/dashboard/analysis");
        mapC1.put("name", "analysis");

        Map<String, Object> mapC2 = new HashMap<>();
        mapC2.put("path", "/dashboard/monitor");
        mapC2.put("name", "monitor");

        childrenList.add(mapC1);
        childrenList.add(mapC2);

        mapP.put("children",childrenList);

        //////////////////
        Map<String, Object> mapP2 = new HashMap<>();
        mapP2.put("path","/form");
        mapP2.put("name","form");
        mapP2.put("icon","picLeft");
//        mapP2.put("authority","admin2");
        Set<Map<String, Object>> childrenList2 = new LinkedHashSet<>();

        Map<String, Object> mapC21 = new HashMap<>();
        mapC21.put("path", "/form/basic-form");
        mapC21.put("name", "basic-form");

        Map<String, Object> mapC22 = new HashMap<>();
        mapC22.put("path", "/form/step-form");
        mapC22.put("name", "step-form");

        childrenList2.add(mapC21);
        childrenList2.add(mapC22);

        mapP2.put("children",childrenList2);

        returnList.add(mapP);
        returnList.add(mapP2);
        return returnList;
    }

}
