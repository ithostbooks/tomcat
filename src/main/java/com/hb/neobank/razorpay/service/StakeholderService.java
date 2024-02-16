package com.hb.neobank.razorpay.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.AccountApiException;
import com.hb.neobank.razorpay.common.StakeholderApiException;
import com.hb.neobank.razorpay.dto.stakeholder.AllStakeholdersResponseDto;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderRequestDto;


public interface StakeholderService {
    public ResponseDTO getStakeholderById(String accountId,String stakeId) throws StakeholderApiException;

    public ResponseDTO addStakeholder(StakeholderRequestDto stakeholderRequestDto, String accountId) throws Exception;

    public ResponseDTO updateStakeholder(StakeholderRequestDto stakeholderRequestDto, String accountId,String stakeHolderId) throws StakeholderApiException, JsonProcessingException, AccountApiException;


    public AllStakeholdersResponseDto getAllStakeholders(String accountId) throws StakeholderApiException;
}
