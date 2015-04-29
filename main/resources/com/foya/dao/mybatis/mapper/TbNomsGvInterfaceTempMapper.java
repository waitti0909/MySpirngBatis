package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbNomsGvInterfaceTemp;
import com.foya.dao.mybatis.model.TbNomsGvInterfaceTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbNomsGvInterfaceTempMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int countByExample(TbNomsGvInterfaceTempExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int deleteByExample(TbNomsGvInterfaceTempExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int insert(TbNomsGvInterfaceTemp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int insertSelective(TbNomsGvInterfaceTemp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    List<TbNomsGvInterfaceTemp> selectByExample(TbNomsGvInterfaceTempExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int updateByExampleSelective(@Param("record") TbNomsGvInterfaceTemp record, @Param("example") TbNomsGvInterfaceTempExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_NOMS_GV_INTERFACE_TEMP
     *
     * @mbggenerated Thu Apr 02 13:04:30 CST 2015
     */
    int updateByExample(@Param("record") TbNomsGvInterfaceTemp record, @Param("example") TbNomsGvInterfaceTempExample example);
}