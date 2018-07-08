package com.yanshou100.core.dao.item;

import com.yanshou100.core.pojo.item.Item;
import com.yanshou100.core.pojo.item.ItemQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDao {
    int countByExample(ItemQuery example);

    int deleteByExample(ItemQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Item record);

    int insertSelective(Item record);

    List<Item> selectByExample(ItemQuery example);

    Item selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemQuery example);

    int updateByExample(@Param("record") Item record, @Param("example") ItemQuery example);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}