package com.bjpowernode.pojo.vo;

public class ProductInfoVo {
    //商品名称的条件封装
    private String pname;
    //商品类型的条件封装
    private Integer typeid;
    //商品的最低价格的条件封装
    private Double lprice;
    //商品的最高价格的条件封装
    private Double hprice;

    public ProductInfoVo() {
    }

    public ProductInfoVo(String pname, Integer typeid, Double lprice, Double hprice) {
        this.pname = pname;
        this.typeid = typeid;
        this.lprice = lprice;
        this.hprice = hprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Double getLprice() {
        return lprice;
    }

    public void setLprice(Double lprice) {
        this.lprice = lprice;
    }

    public Double getHprice() {
        return hprice;
    }

    public void setHprice(Double hprice) {
        this.hprice = hprice;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeid=" + typeid +
                ", lprice=" + lprice +
                ", hprice=" + hprice +
                '}';
    }
}
