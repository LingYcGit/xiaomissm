package com.bjpowernode.controller;


import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    public static final int PAGE_SIZE = 5;

    String saveFileName = "";

    @Autowired
    ProductInfoService productInfoService;

    //不分页，显示所有数据
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的五条数据
    @RequestMapping("/split")
    public String spilt(HttpServletRequest request) {
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return "product";
    }

    //ajax请求翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(int page, HttpSession session) {
        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    //图片上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public String ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成文件名UUID+上传文件的后缀.jpg
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的位置
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request) {
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        int nums = -1;
        try {
            nums = productInfoService.save(info);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg", "添加成功!");
        } else {
            request.setAttribute("msg", "添加失败!");
        }
        //清空saveFileName变量中的内容，为了下次修改或增加ajax异步请求做准备
        saveFileName = "";
        //增加成功后重定向访问数据库
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, Model model) {
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod", info);
        return "update";
    }

    @RequestMapping("/update")
    public String upDate(ProductInfo info, HttpServletRequest request) {
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        //完成更新处理
        int nums = -1;
        try {
            nums = productInfoService.update(info);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (nums > 0) {
            //此时说明更新成功
            request.setAttribute("msg", "更新成功");
        } else {
            //更新失败
            request.setAttribute("msg", "更新失败");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int pid, HttpServletRequest request) {
        int nums = -1;
        try {
            nums = productInfoService.delete(pid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (nums > 0) {
            request.setAttribute("msg", "删除成功");
        } else {
            request.setAttribute("msg", "删除失败");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        //取得第一页的数据
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return request.getAttribute("msg");
    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //pids ="1,4,5"  ps[1,4,5]
        String[] ps = pids.split(",");
        try {
            int num = productInfoService.deleteBatch(ps);
            if (num > 0){
                request.setAttribute("msg","批量删除成功!");
            }else {
                 request.setAttribute("msg","批量删除失败!");
            }
        } catch (Exception exception) {
            request.setAttribute("msg","商品不可删除!");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
