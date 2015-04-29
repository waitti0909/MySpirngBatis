package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsAppFormExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLsAppFormMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int countByExample(TbLsAppFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int deleteByExample(TbLsAppFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int insert(TbLsAppForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int insertSelective(TbLsAppForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    List<TbLsAppForm> selectByExample(TbLsAppFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int updateByExampleSelective(@Param("record") TbLsAppForm record, @Param("example") TbLsAppFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    int updateByExample(@Param("record") TbLsAppForm record, @Param("example") TbLsAppFormExample example);
}