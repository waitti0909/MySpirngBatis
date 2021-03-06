package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.OriginalPowerBill;
import com.foya.dao.mybatis.model.OriginalPowerBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OriginalPowerBillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int countByExample(OriginalPowerBillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int deleteByExample(OriginalPowerBillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int insert(OriginalPowerBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int insertSelective(OriginalPowerBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    List<OriginalPowerBill> selectByExample(OriginalPowerBillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int updateByExampleSelective(@Param("record") OriginalPowerBill record, @Param("example") OriginalPowerBillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORIGINAL_POWER_BILL
     *
     * @mbggenerated Wed Mar 11 15:08:06 CST 2015
     */
    int updateByExample(@Param("record") OriginalPowerBill record, @Param("example") OriginalPowerBillExample example);
}