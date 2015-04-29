package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvToErpMat;
import com.foya.dao.mybatis.model.TbInvToErpMatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvToErpMatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int countByExample(TbInvToErpMatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int deleteByExample(TbInvToErpMatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int insert(TbInvToErpMat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int insertSelective(TbInvToErpMat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    List<TbInvToErpMat> selectByExample(TbInvToErpMatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int updateByExampleSelective(@Param("record") TbInvToErpMat record, @Param("example") TbInvToErpMatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_TO_ERP_MAT
     *
     * @mbggenerated Mon Nov 17 10:07:58 CST 2014
     */
    int updateByExample(@Param("record") TbInvToErpMat record, @Param("example") TbInvToErpMatExample example);
}