package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvBookingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int countByExample(TbInvBookingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int deleteByExample(TbInvBookingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int deleteByPrimaryKey(Long booking_no);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int insert(TbInvBooking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int insertSelective(TbInvBooking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    List<TbInvBooking> selectByExample(TbInvBookingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    TbInvBooking selectByPrimaryKey(Long booking_no);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int updateByExampleSelective(@Param("record") TbInvBooking record, @Param("example") TbInvBookingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int updateByExample(@Param("record") TbInvBooking record, @Param("example") TbInvBookingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int updateByPrimaryKeySelective(TbInvBooking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_BOOKING
     *
     * @mbggenerated Wed Nov 19 15:04:45 CST 2014
     */
    int updateByPrimaryKey(TbInvBooking record);
}