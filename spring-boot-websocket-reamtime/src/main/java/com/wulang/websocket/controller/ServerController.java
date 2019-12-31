package com.wulang.websocket.controller;

import cn.hutool.core.lang.Dict;
import com.wulang.websocket.model.Server;
import com.wulang.websocket.payload.ServerVO;
import com.wulang.websocket.util.ServerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控Controller
 *
 * @author wulang
 * @create 2019/12/20/15:52
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping
    public Dict serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        return ServerUtil.wrapServerDict(serverVO);
    }

}
