package com.ybwh.springboot2.demo.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybwh.springboot2.demo.entity.Area;

public interface AreaDao extends BaseMapper<Area>{

    /**
     * �����Զ����ѯ������������BaseMapper�ķ����������Զ���Ĳ�ѯ��Ҫ��xml�ж����Ӧsql
     * 
     * 
     * @param id
     * @return
     */
    Area selectByPrimaryKey(@Param("id") Integer id);
    
    
    /**
     * �Զ����ҳ������BaseMapper�Դ��ķ�ҳ
     * <p>
     * ��ѯ : ����state״̬��ѯ�û��б���ҳ��ʾ
     * ע��!!: ���������ж��,��Ҫ��ע��ָ��������������xml��ȡֵ
     * </p>
     *
     * @param page ��ҳ����,xml�п��Դ��������ȡֵ,���ݲ��� Page ���Զ���ҳ,������ڵ�һλ(����Լ̳�Pageʵ���Լ��ķ�ҳ����)
     * @param state ״̬
     * @return ��ҳ����
     */
    IPage<Area> selectPageVo(Page<Area> page, @Param("map") Map<String,Object> map);


    
}