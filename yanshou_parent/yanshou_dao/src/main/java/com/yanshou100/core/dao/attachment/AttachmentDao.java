package com.yanshou100.core.dao.attachment;

import com.yanshou100.core.pojo.attachment.Attachment;
import com.yanshou100.core.pojo.attachment.AttachmentQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachmentDao {
    int countByExample(AttachmentQuery example);

    int deleteByExample(AttachmentQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    List<Attachment> selectByExample(AttachmentQuery example);

    Attachment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Attachment record, @Param("example") AttachmentQuery example);

    int updateByExample(@Param("record") Attachment record, @Param("example") AttachmentQuery example);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);
}