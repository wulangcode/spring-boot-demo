package com.wulang.security.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wulang.security.admin.entity.collection.Menu;
import com.wulang.security.admin.entity.collection.MenuRole;
import com.wulang.security.admin.service.MenuRoleService;
import com.wulang.security.admin.service.MenuService;
import com.wulang.security.common.ResultModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: wulang
 * @date: 2019/11/26
 * @description:
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Api(value = "菜单管理", description = "菜单管理接口")
@RequestMapping("/ship/system/menu")
public class MenuController {

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResultModel list(Integer roleId) {
        List<MenuRole> menuList = menuRoleService.list(new QueryWrapper<MenuRole>().eq("role_id", roleId));
        if (menuList.isEmpty()) {
            return ResultModel.selectFailure();
        }
        List<Menu> list = new ArrayList<>();
        for (MenuRole menuRole : menuList) {
            Menu menu = menuService.getOne(new QueryWrapper<Menu>().eq("menu_id", menuRole.getMenuId()));
            list.add(menu);
        }
        List<Menu> tree = findTree(list);
        return ResultModel.selectSuccess(tree);
    }

    private List<Menu> findTree(List<Menu> depts) {
        List<Menu> sysDepts = new ArrayList<>();
        for (Menu dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == -1) {
                dept.setLabel(dept.getName());
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }

    private static void findChildren(List<Menu> sysDepts, List<Menu> depts) {
        for (Menu sysDept : sysDepts) {
            List<Menu> children = new ArrayList<>();
            for (Menu dept : depts) {
                if (sysDept.getMenuId() != null && sysDept.getMenuId().equals(dept.getParentId())) {
                    dept.setLabel(dept.getName());
                    dept.setName(dept.getPath().replace("/", ""));
                    if("用户管理".equals(dept.getLabel()) || "角色管理".equals(dept.getLabel())
                    ||"菜单管理".equals(dept.getLabel()) ||"登录日志".equals(dept.getLabel())){
                        dept.setPUrl("systemManage"+underlineToCamel(dept.getPath()));
                    }else {
                        dept.setPUrl("collectManage"+underlineToCamel(dept.getPath()));
                    }
                    if("水分".equals(dept.getLabel())){
                        dept.setPUrl("collectManage/moisture");
                        dept.setName("moisture");
                    }
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

    public static final char UNDERLINE='_';

    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile(UNDERLINE+"").matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }
}
