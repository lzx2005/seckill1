package com.seckill.web;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillState;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */

@Controller
@RequestMapping("/seckill")//模块
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list"; // /WEB-IN/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execute",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(
            @PathVariable("seckillId") Long seckillId,
            @PathVariable("md5") String md5,
            @CookieValue(value = "killPhone", required = false) Long phone) {

        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未登录");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.END);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.REPEAT);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (Exception e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        }

        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public SeckillResult<Long> time() {
        Date date = new Date();
        return new SeckillResult<Long>(true, date.getTime());
    }
}
