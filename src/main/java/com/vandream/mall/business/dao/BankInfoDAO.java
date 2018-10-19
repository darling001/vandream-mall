package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.BankInfoDTOWithBLOBs;

public interface BankInfoDAO {

    BankInfoDTOWithBLOBs selectByPrimaryKey(String glBankId);
}