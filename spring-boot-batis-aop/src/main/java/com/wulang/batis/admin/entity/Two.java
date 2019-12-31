package com.wulang.batis.admin.entity;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-12-10
 */
public class Two implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Two{" +
        ", id=" + id +
        ", name=" + name +
        ", info=" + info +
        "}";
    }
}
