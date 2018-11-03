package com.ybwh.springboot2.demo.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybwh.springboot2.demo.entity.Area;

public interface AreaDao extends BaseMapper<Area>{

    /**
     * 可以自定义查询方法，而不用BaseMapper的方法，不过自定义的查询需要在xml中定义对应sql
     * 
     * 
     * @param id
     * @return
     */
    Area selectByPrimaryKey(@Param("id") Integer id);
    
    
    /**
     * 自定义分页，不用BaseMapper自带的分页
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<Area> selectPageVo(Page<Area> page, @Param("map") Map<String,Object> map);


    
}