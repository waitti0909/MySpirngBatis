package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.TbInvCategoryExample;
import com.foya.dao.mybatis.model.TbInvCategoryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int countByExample(TbInvCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int deleteByExample(TbInvCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int deleteByPrimaryKey(TbInvCategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int insert(TbInvCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int insertSelective(TbInvCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    List<TbInvCategory> selectByExample(TbInvCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    TbInvCategory selectByPrimaryKey(TbInvCategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int updateByExampleSelective(@Param("record") TbInvCategory record, @Param("example") TbInvCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int updateByExample(@Param("record") TbInvCategory record, @Param("example") TbInvCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int updateByPrimaryKeySelective(TbInvCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Wed Nov 19 15:23:35 CST 2014
     */
    int updateByPrimaryKey(TbInvCategory record);
}