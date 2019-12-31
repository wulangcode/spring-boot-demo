package com.wulang.websocket.util;

import cn.hutool.core.lang.Dict;
import com.wulang.websocket.model.Server;
import com.wulang.websocket.payload.ServerVO;

/**
 * 服务器转换工具类
 *
 * @author wulang
 * @create 2019/12/20/15:21
 */
public class ServerUtil {
    /**
     * 包装成 ServerVO
     *
     * @param server server
     * @return ServerVO
     */
    public static ServerVO wrapServerVO(Server server) {
        ServerVO serverVO = new ServerVO();
        serverVO.create(server);
        return serverVO;
    }

    /**
     * 包装成 Dict
     *
     * @param serverVO serverVO
     * @return Dict
     */
    public static Dict wrapServerDict(ServerVO serverVO) {
        Dict dict = Dict.create()
                .set("cpu", serverVO.getCpu().get(0).getData())
                .set("mem", serverVO.getMem().get(0).getData())
                .set("sys", serverVO.getSys().get(0).getData())
                .set("jvm", serverVO.getJvm().get(0).getData())
                .set("sysFile", serverVO.getSysFile().get(0).getData());
        return dict;
    }
}
