package com.yanshou100.core.dao.news;

import com.yanshou100.core.pojo.news.New;
import com.yanshou100.core.pojo.news.NewQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewDao {
    int countByExample(NewQuery example);

    int deleteByExample(NewQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(New record);

    int insertSelective(New record);

    List<New> selectByExample(NewQuery example);

    New selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") New record, @Param("example") NewQuery example);

    int updateByExample(@Param("record") New record, @Param("example") NewQuery example);

    int updateByPrimaryKeySelective(New record);

    int updateByPrimaryKey(New record);
}