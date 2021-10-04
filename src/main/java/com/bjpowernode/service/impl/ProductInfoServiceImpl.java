package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //select * from product_info limit 起始记录数=(当前页-1)*每页条数，每页条数；
    //select * from product_info limit 10,5;
    @Override
    public PageInfo splitPage(int PageNum, int PageSize) {

        //分页插件使用PagHelper完成分页设置
        PageHelper.startPage(PageNum,PageSize);
        //进行PageInfo的数据封装
        //进行有条件查询，必须创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //select * from product_info order by P_id desc;
        example.setOrderByClause("p_id desc");
        //设置排序，按主键降序排序
        //设置完排序取集合，切记:取集合之前一定要设置PageNum,PageSize
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询到的集合封装到PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }


}
