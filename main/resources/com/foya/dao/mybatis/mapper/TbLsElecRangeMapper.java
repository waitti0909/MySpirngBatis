package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsElecRange;
import com.foya.dao.mybatis.model.TbLsElecRangeExample;
import com.foya.dao.mybatis.model.TbLsElecRangeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLsElecRangeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int countByExample(TbLsElecRangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int deleteByExample(TbLsElecRangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int deleteByPrimaryKey(TbLsElecRangeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int insert(TbLsElecRange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int insertSelective(TbLsElecRange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    List<TbLsElecRange> selectByExample(TbLsElecRangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    TbLsElecRange selectByPrimaryKey(TbLsElecRangeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int updateByExampleSelective(@Param("record") TbLsElecRange record, @Param("example") TbLsElecRangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int updateByExample(@Param("record") TbLsElecRange record, @Param("example") TbLsElecRangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int updateByPrimaryKeySelective(TbLsElecRange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_ELEC_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    int updateByPrimaryKey(TbLsElecRange record);
}