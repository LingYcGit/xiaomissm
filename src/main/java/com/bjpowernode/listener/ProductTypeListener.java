package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从spring中获取ProductTypeServiceImpl对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productType = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productType.getAll();
        //放在全局作用域中，供新增页面，修改页面，前台查询功能提供商品类型的集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
