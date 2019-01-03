package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if(product!=null){
            if(!StringUtils.isBlank(product.getSubImages())){
                String[] images=product.getSubImages().split(",");
                if(images.length>0){
                    product.setMainImage(images[0]);
                }
            }
            if(product.getId()!=null){
                int resultCount=productMapper.updateByPrimaryKey(product);
                if(resultCount>0){
                    return ServerResponse.createBySuccessMsg("更新商品信息成功");
                }
                return ServerResponse.createByErrorMessage("更新商品信息失败");
            }else{
                int resultCount=productMapper.insert(product);       //todo insert的时候id字段为空？？？
                if(resultCount>0){
                    return ServerResponse.createBySuccessMsg("新增商品信息成功");
                }
                return ServerResponse.createByErrorMessage("新增商品信息失败");
            }
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if(productId==null || status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount=productMapper.updateByPrimaryKeySelective(product);
        if(resultCount>0){
            return ServerResponse.createBySuccessMsg("操作成功");
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }
}
