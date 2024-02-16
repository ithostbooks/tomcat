package com.hb.neobank.razorpay.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.razorpay.common.AccountApiException;
import com.hb.neobank.razorpay.common.RazorpayConfig;
import com.hb.neobank.razorpay.common.RazorpayConst;
import com.hb.neobank.razorpay.common.StakeholderApiException;
import com.hb.neobank.razorpay.dao.AccountDao;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import com.hb.neobank.razorpay.dto.stakeholder.AllStakeholdersResponseDto;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderRequestDto;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderResponseDto;
import com.hb.neobank.razorpay.model.StakeholderBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;
import com.hb.neobank.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Base64;

@Service
@Transactional
public class StakeholderServiceImpl implements StakeholderService {

    final String ROOT_URI = "https://api.razorpay.com/v2/accounts";

    @Autowired
    private RazorpayConfig razorPayConfig;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;


    private MultiValueMap<String, String> RAZORPAY_HEADERS;

    public void setHeader() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        map.add("Content-Type", "application/json");
        String ss = this.razorPayConfig.getKeyId() + ":" + this.razorPayConfig.getKeySecret();
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(ss.getBytes());
        map.add(RazorpayConst.AUTH_HAEDER, authHeader);
        this.RAZORPAY_HEADERS = map;
    }

    @Override
    public ResponseDTO getStakeholderById(String accountId, String stakeId) throws StakeholderApiException {
        this.setHeader();
        try {
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            StakeholderResponseDto innerResponse = restTemplateInstance.exchange(ROOT_URI + "/" + accountId + "/stakeholders/" + stakeId, HttpMethod.GET, new HttpEntity<>(this.RAZORPAY_HEADERS), StakeholderResponseDto.class).getBody();
            return ResponseDTO.responseBuilder(200, "stakeHolder", "stakeHolder", "/stakeholder", "stakeHolder", new ObjectMapper().convertValue(innerResponse, StakeholderResponseDto.class));
        } catch (Exception e) {
            throw new StakeholderApiException();
        }

    }

    @Override
    public ResponseDTO addStakeholder(StakeholderRequestDto stakeholderRequestDto, String accountId) throws Exception {
        StakeholderResponseDto stakeResponse = new StakeholderResponseDto();
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setHeader();
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            ResponseDTO responseDTO = new ResponseDTO();
            stakeResponse = restTemplateInstance.exchange(ROOT_URI + "/" + accountId + "/stakeholders", HttpMethod.POST, new HttpEntity<>(new ObjectMapper().writeValueAsString(stakeholderRequestDto), this.RAZORPAY_HEADERS), StakeholderResponseDto.class).getBody();
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            stakeResponse = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), StakeholderResponseDto.class);
        }
        if (stakeResponse != null) {
            if (stakeResponse.getError() != null
                    && stakeResponse.getError().getCode() != null
                    && !stakeResponse.getError().getCode().equals("NA")
                    && stakeResponse.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(stakeResponse.getError().getDescription());
            } else {
                response.setStatus(200);
                response.putData("stakeholder", stakeResponse);
                response.setMessage("Account Created Successfully");
                if (CommonUtil.checkNullEmpty(stakeResponse)) {
                    StakeholderBO stakeholderBO = modelMapper.map(stakeResponse, StakeholderBO.class);
                    SubMerchantBO subMerchant = this.accountDao.getSubMerchantById(accountId);
                    subMerchant.setStakeholder(stakeholderBO);
                    this.accountDao.updateSubMerchant(subMerchant);
                }
            }
        }
        return response;
    }

    @Override
    public ResponseDTO updateStakeholder(StakeholderRequestDto stakeholderRequestDto, String accountId, String stakeHolderId) throws StakeholderApiException, JsonProcessingException, AccountApiException {
        StakeholderResponseDto stakeResponse = new StakeholderResponseDto();
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setHeader();
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            ResponseDTO responseDTO = new ResponseDTO();
            stakeResponse = restTemplateInstance.exchange(ROOT_URI + "/" + accountId + "/stakeholders/" + stakeHolderId, HttpMethod.PATCH, new HttpEntity<>(new ObjectMapper().writeValueAsString(stakeholderRequestDto), this.RAZORPAY_HEADERS), StakeholderResponseDto.class).getBody();
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            stakeResponse = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), StakeholderResponseDto.class);
        }
        if (stakeResponse != null) {
            if (stakeResponse.getError() != null
                    && stakeResponse.getError().getCode() != null
                    && !stakeResponse.getError().getCode().equals("NA")
                    && stakeResponse.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(stakeResponse.getError().getDescription());
            } else {
                response.setStatus(200);
                response.putData("stakeholder", stakeResponse);
                response.setMessage("Account updated Successfully");
                if (CommonUtil.checkNullEmpty(stakeResponse)) {
                    StakeholderBO stakeholderBO = modelMapper.map(stakeResponse, StakeholderBO.class);
                    SubMerchantBO subMerchant = this.accountDao.getSubMerchantById(accountId);
                    subMerchant.setStakeholder(stakeholderBO);
                    this.accountDao.updateSubMerchant(subMerchant);
                }
            }
        }
        return response;

    }

    @Override
    public AllStakeholdersResponseDto getAllStakeholders(String accountId) throws StakeholderApiException {
        this.setHeader();
        RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
        AllStakeholdersResponseDto innerResponse = restTemplateInstance.exchange(ROOT_URI + "/" + accountId + "/stakeholders", HttpMethod.GET, new HttpEntity<>(this.RAZORPAY_HEADERS), AllStakeholdersResponseDto.class).getBody();
        return new ObjectMapper().convertValue(innerResponse, AllStakeholdersResponseDto.class);
    }

}
