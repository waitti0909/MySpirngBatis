package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComPoLineIdMap;
import com.foya.dao.mybatis.model.TbComPoLineIdMapExample;
import com.foya.dao.mybatis.model.TbComPoLineIdMapKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComPoLineIdMapMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int countByExample(TbComPoLineIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int deleteByExample(TbComPoLineIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int deleteByPrimaryKey(TbComPoLineIdMapKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int insert(TbComPoLineIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int insertSelective(TbComPoLineIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    List<TbComPoLineIdMap> selectByExample(TbComPoLineIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    TbComPoLineIdMap selectByPrimaryKey(TbComPoLineIdMapKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int updateByExampleSelective(@Param("record") TbComPoLineIdMap record, @Param("example") TbComPoLineIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int updateByExample(@Param("record") TbComPoLineIdMap record, @Param("example") TbComPoLineIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int updateByPrimaryKeySelective(TbComPoLineIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_PO_LINE_ID_MAP
     *
     * @mbggenerated Thu Jan 15 11:26:35 CST 2015
     */
    int updateByPrimaryKey(TbComPoLineIdMap record);
}