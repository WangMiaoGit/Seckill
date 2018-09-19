package com.wang.web;

import com.wang.dto.Exposer;
import com.wang.dto.SeckillExecution;
import com.wang.dto.SeckillResult;
import com.wang.entity.SecKill;
import com.wang.enums.SeckillStatEnum;
import com.wang.exception.RepeatKillException;
import com.wang.exception.SeckillCloseException;
import com.wang.exception.SeckillException;
import com.wang.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 2017063001 on 2018/9/5.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/5 15:53
 * 修改人  ：2017063001
 * 修改时间：2018/9/5
 * 修改备注：
 */

@Controller//@Service @Component
//url:/模块/资源/{id}/细分/seckill/list
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String List(Model model) {
        //获取列表页
        List<SecKill> seckillList = secKillService.getSeckillList();
        model.addAttribute("list", seckillList);

        //list.jsp + model = ModelAndView
        //WEB-INF/JSP/"list".jsp
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            //重定向到  List的请求上
            return "redirect:/seckill/list";
        }
        SecKill secKill = secKillService.getById(seckillId);
        if (secKill == null) {
            //转发  List的请求上
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", secKill);
        //WEB-INF/JSP/"detail".jsp
        return "detail";
    }


    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@ResponseBody  能将SeckillResult<Exposer> 识别转换成json格式
    public SeckillResult<Exposer> exporser(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = secKillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/exexution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //@CookieValue(value = "killPhone", required = false) Long phone
    //从页面的cookie中拿到用户的手机号
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        //springmvc valid  验证
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            //存储过程调用
            SeckillExecution execution = secKillService.executeSeckillProcedure(seckillId, phone, md5);
//            SeckillExecution execution = secKillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
//            return new SeckillResult<SeckillExecution>(true, e.getMessage());
            return new SeckillResult<SeckillExecution>(true, execution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult(true,now.getTime());
    }
}
