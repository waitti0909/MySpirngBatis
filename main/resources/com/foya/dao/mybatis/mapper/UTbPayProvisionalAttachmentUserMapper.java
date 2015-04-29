package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.noms.dto.pay.TbPayProvisionalAttachmentUserDTO;


public interface UTbPayProvisionalAttachmentUserMapper {
	List<TbPayProvisionalAttachmentUserDTO> selectDesc(String docNo);
}