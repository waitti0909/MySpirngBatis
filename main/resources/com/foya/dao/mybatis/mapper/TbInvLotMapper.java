package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvLot;
import com.foya.dao.mybatis.model.TbInvLotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvLotMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int countByExample(TbInvLotExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int deleteByExample(TbInvLotExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int deleteByPrimaryKey(Long lot_no);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int insert(TbInvLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int insertSelective(TbInvLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    List<TbInvLot> selectByExample(TbInvLotExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    TbInvLot selectByPrimaryKey(Long lot_no);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int updateByExampleSelective(@Param("record") TbInvLot record, @Param("example") TbInvLotExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int updateByExample(@Param("record") TbInvLot record, @Param("example") TbInvLotExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int updateByPrimaryKeySelective(TbInvLot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_LOT
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    int updateByPrimaryKey(TbInvLot record);
}