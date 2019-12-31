package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * (User)实体类
 *
 * @author wulang
 * @since 2019-11-19 10:44:11
 */
@Data
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 551116826424098019L;

    private String username;

    private String password;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Boolean state;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private Integer roleId;

    /**
     * getAuthorities方法本身对应的是roles字段，但由于结构不一致，所以此处新建一个，并在后续进行填充。
     *
     *SimpleGrantedAuthority是GrantedAuthority的一个实现类。Spring Security的权限几乎是用SimpleGrantedAuthority生成的，
     *只要注意每种角色对应一个GrantedAuthority即可。
     * @return
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * isAccountNonExpired、isAccountNonLocked 和 isCredentialsNonExpired 暂且用不到，统一返回
     * true，否则Spring Security会认为账号异常。
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * isEnabled对应state字段，将其代入即可。
     */
    @Override
    public boolean isEnabled() {
        return state;
    }
}