package com.wulang.websocket.payload;

import com.google.common.collect.Lists;
import com.wulang.websocket.model.Server;
import com.wulang.websocket.payload.server.*;
import lombok.Data;

import java.util.List;

/**
 * 服务器信息VO
 *
 * @author wulang
 * @create 2019/12/20/15:23
 */
@Data
public class ServerVO {
    List<CpuVO> cpu = Lists.newArrayList();
    List<JvmVO> jvm = Lists.newArrayList();
    List<MemVO> mem = Lists.newArrayList();
    List<SysFileVO> sysFile = Lists.newArrayList();
    List<SysVO> sys = Lists.newArrayList();

    public ServerVO create(Server server) {
        cpu.add(CpuVO.create(server.getCpu()));
        jvm.add(JvmVO.create(server.getJvm()));
        mem.add(MemVO.create(server.getMem()));
        sysFile.add(SysFileVO.create(server.getSysFiles()));
        sys.add(SysVO.create(server.getSys()));
        return null;
    }
}
