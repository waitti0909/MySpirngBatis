package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvLot;
import java.util.List;


public interface UTbInvLotMapper {
    

    /**
     * select TB_INV_LOT where By mat_no(for Setup)
     * 
     */
    List<TbInvLot> selectLotByMatNo_Setup(TbInvLot record);
    
    /**
     * select TB_INV_LOT where By mat_no(for Unload)
     * 
     */
    List<TbInvLot> selectLotByMatNo_Unload(TbInvLot record);

    

    /**
     * UPDATE TB_INV_LOT.std_qty,setup_qty By mat_no,fa_no    
     * for SetUp
     */
    int updateByMatNoAndFaNo_Setup(TbInvLot record);
    
    /**
     * UPDATE TB_INV_LOT.std_qty By mat_no,fa_no    
     * for Unload
     */
    int updateByMatNoAndFaNo_Unload(TbInvLot record);
}