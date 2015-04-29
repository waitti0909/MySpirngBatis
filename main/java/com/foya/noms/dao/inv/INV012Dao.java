package com.foya.noms.dao.inv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvBookingMapper;
import com.foya.dao.mybatis.mapper.TbInvMaterialMapper;
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
import com.foya.dao.mybatis.mapper.UTbInvTxnMapper;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbInvMrAct;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvSrlExample;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbInvTxnExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvMrDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV012Dao extends BaseDao {

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
	private TbInvMaterialMapper tbInvMaterialMapper;
	
	@Autowired
	private TbInvBookingMapper tbInvBookingMapper;
	
	
	public List<TbInvMaterialOptDTO> search(HashMap<String, Object> dataObj) {

		PageBounds pageBounds = getPageBounds("OPT_ID");
		
		List<TbInvMaterialOptDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper.search",
				dataObj, pageBounds);
		
		PageList<TbInvMaterialOptDTO> pageList = (PageList<TbInvMaterialOptDTO>) list;

		return pageList;
	}

	public List<TbInvMrDDTO> searchUTbInvMrD(String optId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		PageBounds pageBounds = getPageBounds("MAT_NO");
		List<TbInvMrDDTO> list = this.sqlSession
				.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvMrDMapper.searchMrDWithSumQty",
						dataObj, pageBounds);
		PageList<TbInvMrDDTO> pageList = (PageList<TbInvMrDDTO>) list;
		return pageList;
	}
	public TbInvMaterial getMatName(String matNo){
		return tbInvMaterialMapper.selectByPrimaryKey(matNo);
	}
	public TbInvMaterialOpt searchWhId(String optId){
		return tbInvMaterialOptMapper.selectByPrimaryKey(optId);
	}
	public TbInvMrDDTO searchMrDSumQty(String optId,String matNo) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		return uTbInvMrDMapper.searchMrDSumQty(dataObj);
	}
	
	public int searchTxnMRSumQty(String optId, String matNo, String dtlSeq) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("matNo", matNo);
		dataObj.put("dtlSeq", dtlSeq);
		dataObj.put("refActNo", optId);
		dataObj.put("txnType", "1");
		return uTbInvTxnMapper.searchTxnMRSumQty(dataObj);
	}

	public List<TbInvTxnDTO> searchUTbInvTxn(String optId, String matNo,
			Long dtlSeq) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("dtlSeq", dtlSeq);
		PageBounds pageBounds = getPageBounds("inv_txn_no");
		List<TbInvTxnDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchWithInvSrl",
				dataObj, pageBounds);
		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;
		return pageList;
	}

	public int updateTbInvMaterialOpt(String optId, String status, String user,
			Date today) {
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		example.createCriteria().andOPT_IDEqualTo(optId)
				.andOPT_TYPEEqualTo("MRQ");
		TbInvMaterialOpt record = new TbInvMaterialOpt();
		record.setOPT_ID(optId);
		record.setSTATUS(status);
		record.setMD_USER(user);
		record.setMD_TIME(today);
		return tbInvMaterialOptMapper.updateByExampleSelective(record, example);
	}

	public void updateTbInvSrl(TbInvSrlExample example, TbInvSrl record) {
		tbInvSrlMapper.updateByExampleSelective(record, example);
	}
	
	public List<TbInvMrD> searchTbInvMrDMatNo(String optId) {
		TbInvMrDExample example = new TbInvMrDExample();
		example.createCriteria().andOPT_IDEqualTo(optId);
		return tbInvMrDMapper.selectByExample(example);
	}

	public int updateTbInvBooking(String optId, String matNo, String whId,int qty) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		dataObj.put("qty", qty);
		return uTbInvBookingMapper.updateWithInvMrD(dataObj);
	}

	public int updateTbInvInv(int qty, String matNo, String whId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("qty", qty);
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		return uTbInvInvMapper.updateInvWithMatNo(dataObj);
	}

	public List<TbInvTxnDTO> searchTbInvTxn(String txnNo, String whId,
			String crUser, Date crSDate, Date crEDate, String txnType,
			String siteId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("txnNo", txnNo);
		dataObj.put("crUser", crUser);
		dataObj.put("whId", whId);
		dataObj.put("crSDate", crSDate);
		dataObj.put("crEDate", crEDate);
		dataObj.put("txnType", txnType);
		dataObj.put("siteId", siteId);
		PageBounds pageBounds = getPageBounds("TXN_NO");
		List<TbInvTxnDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchInvTxn",
				dataObj, pageBounds);
		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;
		return pageList;
	}

	public List<TbInvTxnDTO> searchWithInvSrlByTxnNo(
			HashMap<String, Object> dataObj) {
		PageBounds pageBounds = getPageBounds("TXN_NO");
		List<TbInvTxnDTO> list = this.sqlSession
				.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchWithInvSrlByTxnNo",
						dataObj, pageBounds);
		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;
		return pageList;
	}

	public List<TbInvTxnDTO> searchWithTxnNoForSp1(
			HashMap<String, Object> dataObj) {
		PageBounds pageBounds = getPageBounds("TXN_NO");
		List<TbInvTxnDTO> list = this.sqlSession
				.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchWithTxnNoForSp1",
						dataObj, pageBounds);
		PageList<TbInvTxnDTO> pageList = (PageList<TbInvTxnDTO>) list;
		return pageList;
	}

	public List<TbInvTxnDTO> searchInvTxnPrintPageDetail(String txnNo) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("txnNo", txnNo);
		return uTbInvTxnMapper.searchInvTxnPrintPageDetail(dataObj);
	}

	public List<TbInvMrDDTO> searchMrD(List<String> optId, String whId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("whId", whId);
		PageBounds pageBounds = getPageBounds("OPT_ID");
		List<TbInvMrDDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvMrDMapper.searchMrD",
				dataObj, pageBounds);
		PageList<TbInvMrDDTO> pageList = (PageList<TbInvMrDDTO>) list;
		return pageList;
	}

	public TbInvTxnDTO selectMaxSeq(String txnNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txnNo", txnNo);
		map.put("prefix", "MD");
		return uTbInvTxnMapper.selerctMaxSeq(map);
	}

	public int updateOpt(TbInvMaterialOpt record) {
		return tbInvMaterialOptMapper.updateByPrimaryKeySelective(record);
	}

	public int updateBooking(HashMap<String, Object> dataObj) {
		return uTbInvBookingMapper.updateBookingQtyMinusQty(dataObj);
	}

	public int updateInvInv(HashMap<String, Object> dataObj) {
		return uTbInvInvMapper.updateInvInvMinusQty(dataObj);
	}

	public int insertInvTxn(TbInvTxn record) {
		return tbInvTxnMapper.insertSelective(record);
	}

	public List<TbInvMaterialOpt> selectInvOpt(String optId) {
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		example.createCriteria().andOPT_IDEqualTo(optId)
				.andOPT_TYPEEqualTo("MRQ");
		example.setDistinct(true);
		return tbInvMaterialOptMapper.selectByExample(example);
	}

	public int insertInvOnhand(TbInvOnhand record) {
		return tbInvOnhandMapper.insertSelective(record);
	}

	public int insertInvMrAct(TbInvMrAct record) {
		return tbInvMrActMapper.insertSelective(record);
	}

	public List<TbInvTxn> selectInvTxn(String txnNo) {
		TbInvTxnExample example = new TbInvTxnExample();
		example.createCriteria().andTxn_noEqualTo(txnNo)
				.andTxn_typeEqualTo(new Byte("1"));
		example.setDistinct(true);
		return tbInvTxnMapper.selectByExample(example);
	}

	public List<TbInvSrl> selectTagNo(String matNo, String whId) {
		TbInvSrlExample example = new TbInvSrlExample();
		example.createCriteria().andMat_noEqualTo(matNo).andWh_idEqualTo(whId)
				.andMat_statusEqualTo(new Byte("1"));
		example.setDistinct(true);
		return tbInvSrlMapper.selectByExample(example);
	}

	public List<TbInvSrl> selectFaNo(String matNo, String whId, String tagNo,
			String venSn) {
		TbInvSrlExample example = new TbInvSrlExample();
		example.createCriteria().andMat_noEqualTo(matNo).andWh_idEqualTo(whId);
		example.setDistinct(true);
		return tbInvSrlMapper.selectByExample(example);
	}

	public List<TbInvMrDDTO> searchMrDOptId(Set<String> optId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		return uTbInvMrDMapper.searchMrDOptId(dataObj);
	}

	public List<TbInvTxnDTO> searchLessQty(HashMap<String, Object> dataObj) {
		return uTbInvTxnMapper.searchLessQty(dataObj);
	}

	/***************************************************************************/
	public List<TbInvTxnDTO> searchTxnDetailByNo(HashMap<String, Object> dataObj) {

		PageBounds pageBounds = getPageBounds("ref_act_no");

		List<TbInvTxnDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbInvTxnMapper.searchInvTxnRef",
				dataObj, pageBounds);

		return list;
	}
	
	public List<TbInvTxnDTO> searchTxnQty(HashMap<String, Object> dataObj) {
		return uTbInvTxnMapper.searchTxnQty(dataObj);
	}
	
	public TbInvMaterial getMaterial(String matNo) {
		return tbInvMaterialMapper.selectByPrimaryKey(matNo);
	}
	public TbInvInvDTO getStdQtyByMatNo(HashMap<String, Object> dataObj) {
		return uTbInvInvMapper.getStdQtyByMatNo(dataObj);
	}
	
	public TbInvSrl getSrlData(Long srlNo) {
		return tbInvSrlMapper.selectByPrimaryKey(srlNo);
	}
	
	public TbInvTxnDTO searchCloseTxnQty(String optId,String matNo,String whId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("refActNo", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("txnType", "1");
		dataObj.put("whId", whId);
		return uTbInvTxnMapper.searchCloseTxnQty(dataObj);
	}
	
	
	public  List<TbInvBooking> selectByExample(TbInvBookingExample example){
		return tbInvBookingMapper.selectByExample(example);
	}
	
	public int updateBooking(TbInvBooking record){
		return tbInvBookingMapper.updateByPrimaryKey(record);
	}
}