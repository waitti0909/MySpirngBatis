package com.foya.noms.dao.inv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvMaterialOptMapper;
import com.foya.dao.mybatis.mapper.TbInvMrActMapper;
import com.foya.dao.mybatis.mapper.TbInvMrDMapper;
import com.foya.dao.mybatis.mapper.TbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.TbInvSrlMapper;
import com.foya.dao.mybatis.mapper.TbInvTxnMapper;
import com.foya.dao.mybatis.mapper.UTbInvBookingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper;
import com.foya.dao.mybatis.mapper.UTbInvMrDMapper;
import com.foya.dao.mybatis.mapper.UTbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.UTbInvRtDMapper;
import com.foya.dao.mybatis.mapper.UTbInvTxnMapper;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbInvTxnExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvRtDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Repository
public class INV015Dao extends BaseDao{
	@Autowired
	private TbInvMaterialOptMapper tbInvMaterialOptMapper; 
	@Autowired
	private UTbInvMaterialOptMapper uTbInvMaterialOptMapper;
	@Autowired
	private TbInvMrDMapper tbInvMrDMapper;
	@Autowired
	private UTbInvMrDMapper uTbInvMrDMapper;
	@Autowired
	private UTbInvBookingMapper uTbInvBookingMapper;
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;
	@Autowired
	private TbInvTxnMapper tbInvTxnMapper;
	@Autowired
	private UTbInvTxnMapper uTbInvTxnMapper;
	@Autowired
	private TbInvOnhandMapper tbInvOnhandMapper;
	@Autowired
	private TbInvMrActMapper tbInvMrActMapper;
	@Autowired
	private TbInvSrlMapper tbInvSrlMapper;
	@Autowired
	private UTbInvOnhandMapper uTbInvOnhandMapper;
	@Autowired
	private UTbInvRtDMapper uTbInvRtDMapper;
	
	public List<TbInvMaterialOptDTO> search(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("OPT_ID");
		List<TbInvMaterialOptDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper.searchRTN",dataObj,  pageBounds);
		PageList<TbInvMaterialOptDTO> pageList = (PageList<TbInvMaterialOptDTO>)list;
		return pageList;
	}
	
	public List<TbInvRtDDTO> searchUTbInvRtD(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("MAT_NO");
		List<TbInvRtDDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvRtDMapper.searchDtl",dataObj,  pageBounds);
		PageList<TbInvRtDDTO> pageList = (PageList<TbInvRtDDTO>)list;
		return pageList;
	}
	public int searchTxnRTSumQty(String optId,String matNo,String dtlSeq){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("matNo", matNo);
		dataObj.put("dtlSeq", dtlSeq);
		dataObj.put("refActNo", optId);
		dataObj.put("txnType", "2");
		return uTbInvTxnMapper.searchTxnMRSumQty(dataObj);
	}
	public List<TbInvRtDDTO> searchFs(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("MAT_NO");
		List<TbInvRtDDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvRtDMapper.searchFs",dataObj,  pageBounds);
		PageList<TbInvRtDDTO> pageList = (PageList<TbInvRtDDTO>)list;
		return pageList;
	}
	
	public List<TbInvTxnDTO> searchTbInvTxnRTDetail(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("txn_no");
		List<TbInvTxnDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchRTDetail",dataObj,  pageBounds);
		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>)list;
		return pageList;
	}
	public List<TbInvTxnDTO> searchTbInvTxnRTDetailNonPage(HashMap<String,Object> dataObj){
		return uTbInvTxnMapper.searchRTDetail(dataObj);
	}
	
	public List<TbInvMrD> searchTbInvMrDMatNo(String optId){
		TbInvMrDExample example = new TbInvMrDExample();
		example.createCriteria().andOPT_IDEqualTo(optId);
		return tbInvMrDMapper.selectByExample(example);
	}

	public int updateTbInvBooking(String optId,String matNo,String whId){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		return uTbInvBookingMapper.updateWithInvMrD(dataObj);
	}
	
	public int updateTbInvInv(int qty,String matNo,String whId){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("qty", qty);
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		return uTbInvInvMapper.updateInvWithMatNo(dataObj);
	}
	
	public List<TbInvTxn> searchTbInvTxn(String txnNo,String whId,String crUser,Date crSDate,Date crEDate,String txnType,String siteId){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("txnNo", txnNo);
		dataObj.put("crUser", crUser);
		dataObj.put("whId", whId);
		dataObj.put("crSDate", crSDate);
		dataObj.put("crEDate", crEDate);
		dataObj.put("txnType", txnType);
		dataObj.put("siteId", siteId);
		PageBounds pageBounds = getPageBounds("TXN_NO");
		List<TbInvTxn> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchInvTxn",dataObj,  pageBounds);
		PageList<TbInvTxn> pageList = (PageList<TbInvTxn>)list;
		return pageList;
	}
	
	public List<TbInvTxnDTO> searchInvTxnPrintPageDetail(String txnNo){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("txnNo", txnNo);
		return uTbInvTxnMapper.searchInvTxnRef(dataObj);
	}
	
	public TbInvTxnDTO selectMaxSeq(String txnNo){
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("txnNo", txnNo);
		map.put("prefix", "RC");
		return uTbInvTxnMapper.selerctMaxSeq(map);
	}

	public int updateInvInv(HashMap<String,Object> dataObj){
		return uTbInvInvMapper.updateByExampleSelectivePlusQty(dataObj);
	}
	public int insertInvTxn(TbInvTxn record){
		return tbInvTxnMapper.insertSelective(record);
	}
	public List<TbInvMaterialOpt> selectInvOpt(String optId){
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		example.createCriteria().andOPT_IDEqualTo(optId).andOPT_TYPEEqualTo("RTN");
		example.setDistinct(true);
		return tbInvMaterialOptMapper.selectByExample(example);
	}
	public int updateOnhandRTNQtyForZero(HashMap<String,Object> dataObj){
		return uTbInvOnhandMapper.updateOnhandRTNQtyForZero(dataObj);
	}
	public int updateOnhandRTNQty(HashMap<String,Object> dataObj){
		return uTbInvOnhandMapper.updateOnhandRTNQty(dataObj);
	}
	public List<TbInvTxn> selectInvTxn(String txnNo){
		TbInvTxnExample example = new TbInvTxnExample();
		example.createCriteria().andTxn_noEqualTo(txnNo).andTxn_typeEqualTo(new Byte("1"));
		example.setDistinct(true);
		return tbInvTxnMapper.selectByExample(example);
	}
	
	public List<TbInvMaterialOpt> selectInvOpt(TbInvMaterialOptExample example){
		return tbInvMaterialOptMapper.selectByExample(example);
	}
	
	/**
	 * 搜尋 Txn Data by Group
	 * 
	 * @param map
	 *            Key:[txnNo,whId,createUser,strDate,endDate]
	 * @return List<TbInvTxnDTO>
	 */
	@SuppressWarnings("null")
	public List<TbInvTxnDTO> getGroupInvTxnDataByGrid(HashMap<String, Object> map) {

		PageBounds pageBounds = getPageBounds("txn_no");
		List<TbInvTxnDTO> invTxnDtoList = sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.getInvTxnDataByGroup",
						map, pageBounds);
		
		// 因 Spec需求(無主檔!!!) ... 需要同張 Table 讀取兩次
		if (invTxnDtoList != null || invTxnDtoList.size() > 0) {
			for (TbInvTxnDTO txnDto : invTxnDtoList) {
				
				HashMap<String, Object> txnMap = new HashMap<String,Object>();
				txnMap.put("txnNo", txnDto.getTxn_no());
				
				txnDto.setInvTxnDtoList(getInvTxnDataByGrid(txnMap));
			}
		}

		return invTxnDtoList;
	}

	/**
	 * 搜尋 Txn Data
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	public List<TbInvTxnDTO> getInvTxnData(String txnNo, String whId,
			String createUser, Date strDate, Date endDate) {
		
		HashMap<String, Object> map =  new HashMap<String, Object>();
		map.put("txnNo", txnNo);
		PageBounds pageBounds = getPageBounds("txn_no");
		List<TbInvTxnDTO> list = sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.getInvTxnData",
				map, pageBounds);
		
		return list;
	}
	
	/**
	 * 搜尋 Txn Data by Group
	 * 
	 * @param map
	 *            Key:[txnNo,whId,createUser,strDate,endDate]
	 * @return List<TbInvTxnDTO>
	 */
	public List<TbInvTxnDTO> getInvTxnDataByGrid(HashMap<String, Object> map){
			
		for(String keys : map.keySet()){
			System.err.println(map.get(keys));
		}
		
		PageBounds pageBounds = getPageBounds("mat_no");
		List<TbInvTxnDTO> list = sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.getInvTxnData",
				map, pageBounds);
		
		return list;
	}
	
	public List<TbInvRtDDTO> searchRtDOptId(Set<String> optId){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("optId", optId);
		return uTbInvRtDMapper.searchRtDOptId(dataObj);
	}
	public TbInvMaterialOpt searchWhId(String optId){
		return tbInvMaterialOptMapper.selectByPrimaryKey(optId);
	}
	public List<TbInvTxnDTO> searchLessQty(HashMap<String,Object> dataObj){		
		return uTbInvTxnMapper.searchLessQty(dataObj);
	}
	public List<TbInvTxnDTO> searchTxnRTQty(HashMap<String, Object> dataObj) {
		return uTbInvTxnMapper.searchTxnRTQty(dataObj);
	}
	public TbInvRtDDTO searchRtDSumQty(String optId,String matNo) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		return uTbInvRtDMapper.searchRtDSumQty(dataObj);
	}
	
	public TbInvOnhandDTO getOnhandQty(HashMap<String, Object> dataObj) {
		return uTbInvOnhandMapper.getOnhandQty(dataObj);
	}
}