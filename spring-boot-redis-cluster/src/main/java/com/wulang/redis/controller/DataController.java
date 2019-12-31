package com.wulang.redis.controller;//package com.wulang.redis.controller;
//
//import com.wulang.redis.utils.JedisPoolCacheUtils;
//import com.wulang.redis.utils.util.Detect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @author wulang
// * @create 2019/11/26/15:45
// */
//@Controller
//@RequestMapping("/data")
//public class DataController {
//
//    private static Logger log = LoggerFactory.getLogger(DataController .class);
////    @Autowired
////    private IDataService dataService;
//
//    @RequestMapping(value="/mkDetails",method= RequestMethod.POST)
//    public void mkDetails(HttpServletResponse hResponse, HttpServletRequest request) throws IOException {
//        hResponse.setCharacterEncoding("UTF-8");
//        hResponse.setContentType("text/html;charset=utf-8");
//        //跨域请求  * 允许所有
//        hResponse.setHeader("Access-Control-Allow-Origin", "*");
//        hResponse.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
//        hResponse.setHeader("Access-Control-Max-Age", "3600");
//        hResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        PrintWriter out = hResponse.getWriter();
////        Response response = new Response();
////        response.setSuccess(true);
//        String user= (String) request.getParameter("user");
//        //TODO 注释代码只是为了启动程序
//        if (Detect.notEmpty(123)) {
////        if (Detect.notEmpty(mac)) {
//            String redisResponse = JedisPoolCacheUtils.getVStr(user.trim(), 0);
//            //Response parseObject = JSON.parseObject(redisResponse,Response.class);
//            if (Detect.notEmpty(redisResponse)) {
//                log.info("=======>redis "+redisResponse);
////                JsonUtil.writeDirect(out, redisResponse);
//                //JsonUtil.write(out, parseObject);
//            }else {
////                response = dataService.mkDetails(user.trim());
////                log.info("=======>mysql "+response.toString());
////                JedisPoolCacheUtils.setVExpire(user.trim(), response, JedisPoolCacheUtils.EXRP_HALF_DAY, 0);
//            }
//        }else {
////            response.setSuccess(false);
////            response.setCode(4001);
////            response.setMsg("参数无效!");
//        }
////        JsonUtil.write(out, response);
//    }
//
//}
