package com.wulang.security.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wulang.security.admin.entity.collection.AuthoritiesVO;
import com.wulang.security.admin.entity.collection.Menu;
import com.wulang.security.admin.entity.collection.Role;
import com.wulang.security.admin.entity.collection.RoleVO;
import com.wulang.security.admin.service.MenuRoleService;
import com.wulang.security.admin.service.MenuService;
import com.wulang.security.admin.service.RoleService;
import com.wulang.security.common.ResultModel;
import com.wulang.security.common.utils.TreeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: wulang
 * @date: 2019/11/23
 * @description:
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Api(value = "角色管理", description = "角色管理接口")
@RequestMapping("/ship/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private TreeUtils treeUtils;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色", produces = "application/json")
    public ResultModel add(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getRoleName())) {
            return ResultModel.failure1("角色名不能为空");
        }
        roleService.save(new Role().setRoleName(role.getRoleName()).setRole("ROLE_" + role.getRoleName())
                .setRemake(role.getRemake()).setCreateTime(new Date()));
        return ResultModel.createSuccess();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改角色", produces = "application/json")
    public ResultModel update(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getRoleName())) {
            return ResultModel.failure1("角色名不能为空");
        }
        boolean update = roleService.update(new UpdateWrapper<Role>().set("role_name", role.getRoleName())
                .set("remake", role.getRemake()).set("role", "ROLE_" + role.getRoleName()).eq("id", role.getId()));
        if (update) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.updateFailure();
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询角色列表", produces = "application/json")
    public ResultModel list() {
        List<Role> list = roleService.list();
        if (list == null) {
            return ResultModel.selectFailure();
        }
        return ResultModel.selectSuccess(list);
    }

    @ApiOperation(value = "查询角色权限树", produces = "application/json")
    @GetMapping("/authTree")
    public ResultModel getUserJobRecursionCategoryList(Integer roleId) {
        if (roleId != null) {
            List<Menu> menuList = menuService.listByRoleId(roleId);
            List<Menu> allAuths = menuService.list();
            List<AuthoritiesVO> authTrees = new ArrayList<>();
            for (Menu one : allAuths) {
                AuthoritiesVO authTree = new AuthoritiesVO();
                authTree.setMenuId(one.getMenuId());
                authTree.setLabel(one.getName());
                authTree.setParentId(one.getParentId());
                authTree.setChecked(false);
                for (Menu temp : menuList) {
                    if (temp.getMenuId().equals(one.getMenuId())) {
                        authTree.setChecked(true);
                        break;
                    }
                }
                authTrees.add(authTree);
            }
            List<AuthoritiesVO> tree = treeUtils.findTree(authTrees);
            return ResultModel.selectSuccess(tree);
        }
        List<Menu> tree = findTree();
        return ResultModel.selectSuccess(tree);
    }

    @PostMapping("/updateRoleAuth")
    @ApiOperation(value = "更改角色资源", produces = "application/json")
    public ResultModel updateRoleAuth(@RequestBody RoleVO roleVO) {
        if (menuRoleService.updateRoleAuth(roleVO.getRoleId(), roleVO.getAuthIds())) {
            return ResultModel.updateSuccess();
        }
        return ResultModel.updateFailure();
    }

    private List<Menu> findTree() {
        List<Menu> sysDepts = new ArrayList<>();
        List<Menu> depts = menuService.list();
        for (Menu dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == -1) {
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
                    dept.setName(dept.getName());
                    dept.setLabel(dept.getName());
                    children.add(dept);
                }
                dept.setLabel(dept.getName());
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }
}
