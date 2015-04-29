package com.foya.noms.dao.pay;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbPayProvisionalAttachmentMapper;
import com.foya.dao.mybatis.mapper.TbPayProvisionalAttachmentUserMapper;
import com.foya.dao.mybatis.mapper.UTbPayProvisionalAttachmentUserMapper;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachment;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentExample;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentUser;
import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentUserExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayProvisionalAttachmentUserDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Repository
public class Pay014Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayProvisionalAttachmentMapper tbPayProvisionalAttachmentMapper;
	@Autowired
	private UTbPayProvisionalAttachmentUserMapper uTbPayProvisionalAttachmentUserMapper;
	@Autowired
	private TbPayProvisionalAttachmentUserMapper tbPayProvisionalAttachmentUserMapper;
	
	public List<TbPayProvisionalAttachment> selectByExample(TbPayProvisionalAttachmentExample example){
		PageBounds pageBounds = getPageBounds("DOCUMENT_NO");
		List<TbPayProvisionalAttachment> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.TbPayProvisionalAttachmentMapper.selectByExample",example,  pageBounds);
		PageList<TbPayProvisionalAttachment> pageList = (PageList<TbPayProvisionalAttachment>)list;
		return pageList;
	}
	
	public TbPayProvisionalAttachment selectByPrimaryKey(String docNo){
		return tbPayProvisionalAttachmentMapper.selectByPrimaryKey(docNo);
	}
	
	public List<TbPayProvisionalAttachmentUserDTO> selectDesc(String docNo){
		PageBounds pageBounds = getPageBounds("DOCUMENT_NO");
		List<TbPayProvisionalAttachmentUserDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayProvisionalAttachmentUserMapper.selectDesc",docNo,  pageBounds);
		PageList<TbPayProvisionalAttachmentUserDTO> pageList = (PageList<TbPayProvisionalAttachmentUserDTO>)list;
		return pageList;
	}
	
	public int insertPayAttach(TbPayProvisionalAttachment record){
		return tbPayProvisionalAttachmentMapper.insertSelective(record);
	}
	
	public int insertPayAttachUser(TbPayProvisionalAttachmentUser record){
		return tbPayProvisionalAttachmentUserMapper.insertSelective(record);
	}
	
	public int delMasterData(String docNo){
		return tbPayProvisionalAttachmentMapper.deleteByPrimaryKey(docNo);
	}
	
	public int delDetailData(TbPayProvisionalAttachmentUserExample example){
		return tbPayProvisionalAttachmentUserMapper.deleteByExample(example);
	}
}
