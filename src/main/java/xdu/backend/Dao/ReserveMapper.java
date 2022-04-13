package xdu.backend.Dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Reserve;
import xdu.backend.pojo.ReserveExample;

@Repository
public interface ReserveMapper {
    int deleteByExample(ReserveExample example);

    int deleteByPrimaryKey(String book_id);

    int insert(Reserve record);

    int insertSelective(Reserve record);

    List<Reserve> selectByExample(ReserveExample example);

    Reserve selectByPrimaryKey(String book_id);

    int updateByExampleSelective(@Param("record") Reserve record, @Param("example") ReserveExample example);

    int updateByExample(@Param("record") Reserve record, @Param("example") ReserveExample example);

    int updateByPrimaryKeySelective(Reserve record);

    int updateByPrimaryKey(Reserve record);
}