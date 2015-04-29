package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvBookingMapper;
import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvMaterialMapper;
import com.foya.dao.mybatis.mapper.TbInvTrDtlMapper;
import com.foya.dao.mybatis.mapper.TbInvTrMapper;
import com.foya.dao.mybatis.mapper.UTbInvBookingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrActMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrDtlMapper;
import com.foya.dao.mybatis.mapper.UTbInvTrMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialExample;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.dao.mybatis.model.TbInvTrDtlExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvTrDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Repository
public class INV007Dao extends BaseDao{
	@Autowired
	private TbInvTrMapper tbInvTrMapper;
	@Autowired
	private UTbInvTrMapper utbInvTrMapper;
	@Autowired
	private TbInvTrDtlMapper tbInvTrDtlMapper;
	@Autowired
	private TbInvInvMapper tbInvInvMapper;
	@Autowired
	private TbInvBookingMapper tbInvBookingMapper;
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;
	@Autowired
	private UTbInvTrDtlMapper uTbInvTrDtlMapper;
	@Autowired
	private UTbInvTrActMapper uTbInvTrActMapper;
	@Autowired
	private TbInvMaterialMapper tbInvMaterialMapper;
	@Autowired
	private UTbInvBookingMapper uTbInvBookingMapper;
	
	public List<TbInvTr> search(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("TR_NO");
		List<TbInvTr> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrMapper.searchInvTrByInitSearch",dataObj,  pageBounds);
		/* 調出倉改由欄位判斷 不再計算金額是否相符
		 * if ("0".equals(dataObj.get("checkOutWareHouse"))
				&& "7".equals(dataObj.get("statusSelect"))) {
			for (int i = 0; i < list.size(); i++) {
				TbInvTr trObj = (TbInvTr) list.get(i);
				String trNo = trObj.getTr_no();
				int dtlSum = tbInvTrMapper
						.searchInvDtlCntByStatusSevenWareHouseClose(trNo);
				int actSum = tbInvTrMapper
						.searchInvActCntByStatusSevenWareHouseClose(trNo);
				if (dtlSum != actSum) {
					list.remove(i);
				}
			}
		}
		*/
		PageList<TbInvTr> pageList = (PageList<TbInvTr>)list;
		return pageList;
	}
	public TbInvTrDTO searchTrMasterDscr(HashMap<String,String> dataObj){
		return utbInvTrMapper.searchMasterColumsDscr(dataObj);
	}	
	
	public List<TbInvTrDtlDTO> searchDtlPage(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("TR_NO");
		List<TbInvTrDtlDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTrDtlMapper.searchDtl",dataObj,  pageBounds);
		PageList<TbInvTrDtlDTO> pageList = (PageList<TbInvTrDtlDTO>)list;		
		
       return pageList;
	}
	public List<TbInvTrDtlDTO> searchDtlSeq(HashMap<String,Object> dataObj){
		return uTbInvTrDtlMapper.searchDtlSeq(dataObj);
	}
	
	public int searchDtlAmt(HashMap<String,Object> dataObj){
		return uTbInvTrDtlMapper.searchDtlAmt(dataObj);
	}
	public int searchTrActAmt(String trNo,String  matNo,int  trIn,Long dtlSeq,String returnTr){
		return 	uTbInvTrActMapper.getActExportNumberGroup(trNo, matNo, trIn, dtlSeq,returnTr);
	}
	public List<TbInvInv> getInvQty(TbInvInvExample example){
		return tbInvInvMapper.selectByExample(example);
	}
	
	public List<TbInvInvDTO> addDtlRowCheck(HashMap<String,String> dataObj){
		return uTbInvInvMapper.selectInvInvWithCtrlType(dataObj);
	}
	
	public String insertTbInvTrSelective(TbInvTr data){
		String returnMsg="success";
		try{
			tbInvTrMapper.insertSelective(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String insertTbInvTrDtlSelective(TbInvTrDtl data){
		String returnMsg="success";
		try{
			tbInvTrDtlMapper.insertSelective(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String updateTbInvTrSelective(TbInvTr data){
		String returnMsg="success";
		try{
			tbInvTrMapper.updateByPrimaryKeySelective(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String updateTbInvTrDtlSelective(TbInvTrDtl data,TbInvTrDtlExample example){
		String returnMsg="success";
		try{
			tbInvTrDtlMapper.updateByExampleSelective(data,example);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String deleteTbInvTrDtlByPrimarykey(TbInvTrDtlExample data){
		String returnMsg="success";
		try{			
			tbInvTrDtlMapper.deleteByExample(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public TbInvTr selectMaxSeq(String trNo){
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("trNo", trNo);
		return utbInvTrMapper.selerctMaxSeq(map);
	}
	
	public String deleteByPrimaryKey(String trNo){
		TbInvTr data = new TbInvTr();
		data.setTr_no(trNo);
		data.setStatus(new Byte("8"));
		String returnMsg="success";
		try{			
			tbInvTrMapper.updateByPrimaryKeySelective(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String insertTbInvBookingSelective(TbInvBooking data){
		String returnMsg="success";
		try{
			tbInvBookingMapper.insertSelective(data);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public String updateTbInvInvSelective(TbInvInv data,String whId,String matNo){
		String returnMsg="success";
		try{
			TbInvInvExample example = new TbInvInvExample();
			example.createCriteria().andMat_noEqualTo(matNo).andWh_idEqualTo(whId);			
			uTbInvInvMapper.updateByExampleSelectivePlusAppQty(data, example);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public List<TbInvInvDTO> selectForBatchExcel(HashMap<String,Object> dataObj){
		return uTbInvInvMapper.selectForBatchExcel(dataObj);
	}	
	public List<TbInvMaterial> selectMaterial(TbInvMaterialExample example){
		return tbInvMaterialMapper.selectByExample(example);
	}
	
	public TbInvTr selectByPrimaryKey(String trNo){
		return tbInvTrMapper.selectByPrimaryKey(trNo);
	}
	
	public List<TbInvTrDtl> selectDtlByExample(TbInvTrDtlExample example){
		return tbInvTrDtlMapper.selectByExample(example);
	}
	
	public int updateInvInvByExampleSelectiveMinusAppQty(HashMap<String,Object> dataObj){
		return uTbInvInvMapper.updateByExampleSelectiveMinusAppQty(dataObj);
	}
	
	public int updateBookingQtyMinusQtyByDtl(HashMap<String,Object> dataObj){
		return uTbInvBookingMapper.updateBookingQtyMinusQtyByDtl(dataObj);
	}
	
	public int deleteBookingQty(TbInvBookingExample example){
		return tbInvBookingMapper.deleteByExample(example);
	}
}
