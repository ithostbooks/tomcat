package com.hb.neobank.razorpay.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.CommonListTO;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.razorpay.common.AccountApiException;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.common.RazorpayConfig;
import com.hb.neobank.razorpay.common.RazorpayConst;
import com.hb.neobank.razorpay.dao.AccountDao;
import com.hb.neobank.razorpay.dao.ProductConfigDao;
import com.hb.neobank.razorpay.dto.AccountDto;
import com.hb.neobank.razorpay.dto.DocumentResponseTO;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigAddTO;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigResponseTO;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigTO;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import com.hb.neobank.razorpay.model.*;
import com.hb.neobank.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    public String ROOT_URI = "https://api.razorpay.com/v2/accounts";


    @Autowired
    private RazorpayConfig razorPayConfig;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ProductConfigDao productConfigDao;


    private MultiValueMap<String, String> RAZORPAY_HEADERS;

    HttpHeaders fileHeader;


    public void setHeader() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        map.add("content-type", "application/json");
        String ss = this.razorPayConfig.getKeyId() + ":" + this.razorPayConfig.getKeySecret();
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(ss.getBytes());
        map.add(RazorpayConst.AUTH_HAEDER, authHeader);
        this.RAZORPAY_HEADERS = map;
    }

    public void setFileHeader() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        String ss = this.razorPayConfig.getKeyId() + ":" + this.razorPayConfig.getKeySecret();
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(ss.getBytes());
        map.add(RazorpayConst.AUTH_HAEDER, authHeader);
        this.RAZORPAY_HEADERS = map;
    }

    @Override
    public SubMerchantTO getSubmerchantById(String id) throws AccountApiException {
        this.setHeader();
        RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
        SubMerchantTO innerResponse = restTemplateInstance.exchange(ROOT_URI + "/" + id, HttpMethod.GET, new HttpEntity<>(this.RAZORPAY_HEADERS), SubMerchantTO.class).getBody();
        return new ObjectMapper().convertValue(innerResponse, SubMerchantTO.class);
    }

    @Override
    public ResponseDTO getSubMerchantByIdDao(String id) throws AccountApiException {
        SubMerchantBO subMerchant = this.accountDao.getSubMerchantById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(200);
        responseDTO.putData("subMerchant", subMerchant);
        return responseDTO;
    }

    @Override
    public ResponseDTO getDocuments(String id) throws AccountApiException {
        List<DocumentBO> documents = this.accountDao.getDocument(id);
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.putData("documents", documents);
        return response;
    }


    @Override
    public ResponseDTO addSubmerchantAccount(AccountDto accountDto) throws AccountApiException, JsonProcessingException {
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        SubMerchantTO subMerchant = new SubMerchantTO();
        try {
            this.setHeader();
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            Map<String, Object> map = handleAccountData(accountDto);
            String stringDto = mapper.writeValueAsString(map);
            HttpEntity<String> entity = new HttpEntity<>(stringDto, this.RAZORPAY_HEADERS);
            SubMerchantTO accountResponse = restTemplateInstance.exchange(ROOT_URI, HttpMethod.POST, entity, SubMerchantTO.class).getBody();
            if (CommonUtil.checkNullEmpty(accountResponse) && Objects.equals(accountResponse.getStatus(), "created")) {
                SubMerchantBO subMerchantBO = this.convertToBO(accountResponse);
                if (accountDto.isDefaultFlag()) {
                    accountResponse.setDefaultFlag(accountDto.isDefaultFlag());
                    subMerchantBO.setDefaultFlag(true);
                    SubMerchantTO subMerchantdefault = this.accountDao.getSubMerchantList();
                    if(CommonUtil.checkNullEmpty(subMerchantdefault)){
                        subMerchantdefault.setDefaultFlag(false);
                        this.updateSubMerchant(subMerchantdefault);
                    }
                }
                this.accountDao.addSubMerchant(subMerchantBO);
            }
            if (CommonUtil.checkNullEmpty(accountResponse)) {
                response.setStatus(200);
                response.putData("submerchant", accountResponse);
                response.setMessage("Account Created Successfully");
            }
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            subMerchant = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), SubMerchantTO.class);
        }
        if (subMerchant != null) {
            if (subMerchant.getError() != null
                    && subMerchant.getError().getCode() != null
                    && !subMerchant.getError().getCode().equals("NA")
                    && subMerchant.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(subMerchant.getError().getDescription());
            }
        }
        return response;
    }

    @Override
    public ResponseDTO updateSubmerchantAccount(String id, AccountDto accountDto) throws AccountApiException, JsonProcessingException {
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        SubMerchantTO subMerchant = new SubMerchantTO();
        try {
            this.setHeader();
            AccountDto accountTO = new AccountDto();
            accountTO = modelMapper.map(accountDto, AccountDto.class);
            String stringDto = mapper.writeValueAsString(accountTO);
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(stringDto, this.RAZORPAY_HEADERS);
            SubMerchantTO accountResponse = restTemplateInstance.exchange(ROOT_URI + "/" + id, HttpMethod.PATCH, entity, SubMerchantTO.class).getBody();
            if (CommonUtil.checkNullEmpty(accountResponse) && Objects.equals(accountResponse.getStatus(), "created")) {
                SubMerchantBO subMerchantBO = this.convertToBO(accountResponse);
                if (accountDto.isDefaultFlag()) {
                    subMerchantBO.setDefaultFlag(true);
                    accountResponse.setDefaultFlag(accountDto.isDefaultFlag());
                    SubMerchantTO subMerchantdefault = this.accountDao.getSubMerchantList();
                    if(CommonUtil.checkNullEmpty(subMerchantdefault)){
                        subMerchantdefault.setDefaultFlag(false);
                        this.updateSubMerchant(subMerchantdefault);
                    }
                }
                this.accountDao.updateSubMerchant(subMerchantBO);
            }
            if (CommonUtil.checkNullEmpty(accountResponse)) {
                response.setStatus(200);
                response.putData("submerchant", accountResponse);
                response.setMessage("Account Updated Successfully");
            }
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            subMerchant = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), SubMerchantTO.class);
        }
        if (subMerchant != null) {
            if (subMerchant.getError() != null
                    && subMerchant.getError().getCode() != null
                    && !subMerchant.getError().getCode().equals("NA")
                    && subMerchant.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(subMerchant.getError().getDescription());
            }
        }
        return response;
    }

    @Override
    public ResponseDTO uploadDocument(String stakeholderId, String subMerchantId, String document_type, MultipartFile file, String url) throws RazorpayApiException, JsonProcessingException {
        ResponseDTO response = new ResponseDTO();
        String reqUrl = "https://api.razorpay.com/v2/" + url;
        ObjectMapper mapper = new ObjectMapper();
        DocumentResponseTO innerResponse = new DocumentResponseTO();
        try {
            this.setFileHeader();
            if (CommonUtil.checkNullEmpty(file)) {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", file.getResource());
                body.add("document_type", document_type);
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, this.RAZORPAY_HEADERS);
                RestTemplate restTemplate = new RestTemplate();
                RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
                innerResponse = restTemplateInstance.exchange(reqUrl, HttpMethod.POST, requestEntity, DocumentResponseTO.class).getBody();
            }
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            innerResponse = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), DocumentResponseTO.class);
        }
        if (CommonUtil.checkNullEmpty(innerResponse)) {
            if (innerResponse.getError() != null) {
                response.setStatus(400);
                response.setMessage(innerResponse.getError().getDescription() + " " + document_type);
            } else {
                response.setStatus(200);
                response.putData("document", innerResponse);
                response.setMessage("Document Uploaded Successfully");
                this.handleDocumentsAddUpdate(innerResponse, subMerchantId, stakeholderId, url, document_type);
            }
        }
        return response;
    }

    @Override
    public SubMerchantTO deleteSubmerchantAccount(String id) throws AccountApiException {
        this.setHeader();
        RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
        SubMerchantTO innerResponse = restTemplateInstance.exchange(ROOT_URI + "/" + id, HttpMethod.DELETE, new HttpEntity<>(this.RAZORPAY_HEADERS), SubMerchantTO.class).getBody();
        if (CommonUtil.checkNullEmpty(innerResponse) && innerResponse.getStatus().equals("suspended")) {
            this.accountDao.deleteSubMerchant(id);
        }
        return new ObjectMapper().convertValue(innerResponse, SubMerchantTO.class);
    }

    @Override
    public ResponseDTO getSubMerchantList(HashMap<String, Integer> apiReqData) {
        List<SubMerchantTO> responseTO = new ArrayList<>();
        CommonListTO<SubMerchantTO> listData = new CommonListTO<>();
        ResponseDTO response = new ResponseDTO();
        CommonListTO<SubMerchantBO> list = this.accountDao.getSubMerchantList(apiReqData);
        list.getDataList().forEach(element -> {
            SubMerchantTO account =modelMapper.map(element, SubMerchantTO.class);
            account.setContact_name(element.getContact_name());
            responseTO.add(account);

        });
        listData.setDataList(responseTO);
        listData.setTotalRow(list.getTotalRow());
        listData.setPageCount(list.getPageCount());
        response.setStatus(200);
        response.setMessage("List Fetched Successfully");
        response.putData("submerchant", listData);
        response.putData("totalRow", list.getTotalRow());
        return response;
    }

    @Override
    public ResponseDTO getTermsAndConditions() throws JsonProcessingException {
        String reqUrl = "https://api.razorpay.com/v2/products/payments/tnc";
        this.setHeader();
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        SubMerchantTO subMerchant = new SubMerchantTO();
        try {
            this.setHeader();
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            ResponseEntity<String> innerResponse = restTemplateInstance.exchange(reqUrl, HttpMethod.GET, new HttpEntity<>(this.RAZORPAY_HEADERS), String.class);
            String resString = innerResponse.getBody();
            subMerchant = modelMapper.map(mapper.readValue(resString, Object.class), SubMerchantTO.class);
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            subMerchant = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), SubMerchantTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (subMerchant != null) {
            if (subMerchant.getError() != null
                    && subMerchant.getError().getCode() != null
                    && !subMerchant.getError().getCode().equals("NA")
                    && subMerchant.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(subMerchant.getError().getDescription());
            } else {
                response.setStatus(200);
                response.putData("tnc", subMerchant);
                response.setMessage("tnc Fetched Successfully");
            }
        }
        return response;
    }

    @Override
    public ResponseDTO addProductConfiguration(ProductConfigTO productConfig) throws AccountApiException, JsonProcessingException {
        String reqUrl = "https://api.razorpay.com/v2/accounts/" + productConfig.getAccount_id() + "/products";
        ObjectMapper mapper = new ObjectMapper();
        ResponseDTO response = new ResponseDTO();
        ProductConfigResponseTO productResponse = new ProductConfigResponseTO();
        ProductConfigAddTO productConfigTO = new ProductConfigAddTO();
        try {
            this.setHeader();
            productConfigTO.setProduct_name("payment_links");
            productConfigTO.setTnc_accepted(productConfig.isTnc_accepted());
            String stringDto = mapper.writeValueAsString(productConfigTO);
            HttpEntity<String> entity = new HttpEntity<>(stringDto, this.RAZORPAY_HEADERS);
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            productResponse = restTemplateInstance.exchange(reqUrl, HttpMethod.POST, entity, ProductConfigResponseTO.class).getBody();
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            productResponse = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), ProductConfigResponseTO.class);
        }
        if (productResponse != null) {
            if (productResponse.getError() != null
                    && productResponse.getError().getCode() != null
                    && !productResponse.getError().getCode().equals("NA")
                    && productResponse.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(productResponse.getError().getDescription());
            } else {
                response.setStatus(200);
                response.putData("productConfig", productResponse);
                response.setMessage("productResponse Added Successfully");
                if (CommonUtil.checkNullEmpty(productResponse)) {
                    ProductConfigBO productConfiguration = (modelMapper.map(productResponse, ProductConfigBO.class));
                    productConfiguration.setAccount_id(productConfig.getAccount_id());
                    productConfiguration.setTnc_accepted(productResponse.getTnc().isAccepted());
                    this.productConfigDao.addProductConfig(productConfiguration);
                }
            }
        }
        return response;
    }

    @Override
    public ResponseDTO getProductConfiguration(String accountId) throws AccountApiException, JsonProcessingException {
        ProductConfigBO productConfig = this.productConfigDao.getProductConfig(accountId);
        ResponseDTO response = new ResponseDTO();
        if (CommonUtil.checkNullEmpty(productConfig)) {
            response.setStatus(200);
            response.putData("productConfig", productConfig);
            response.setMessage("productResponse Fetched Successfully");
        } else {
            response.setStatus(400);
            response.setMessage("Error While Fetching List");
        }
        return response;
    }

    @Override
    public ProductConfigResponseTO fetchProductConfiguration(String accountId, String productId) throws AccountApiException, JsonProcessingException {
        String reqUrl = "https://api.razorpay.com/v2/accounts/" + accountId + "/products" + "/" + productId;
        ObjectMapper mapper = new ObjectMapper();
        this.setHeader();
        RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
        ProductConfigResponseTO innerResponse = restTemplateInstance.exchange(reqUrl, HttpMethod.GET, new HttpEntity<>(this.RAZORPAY_HEADERS), ProductConfigResponseTO.class).getBody();
        innerResponse.setTnc_accepted(innerResponse.getTnc().isAccepted());
        ProductConfigBO productConfig = this.productConfigDao.getProductConfig(accountId);
        if (CommonUtil.checkNullEmpty(productConfig) && CommonUtil.checkNullEmpty(productConfig.getActive_configuration()) && CommonUtil.checkNullEmpty(productConfig.getActive_configuration().getSettlements()) && CommonUtil.checkNullEmpty(productConfig.getActive_configuration().getSettlements().getBankId()))
            innerResponse.setBankId(productConfig.getActive_configuration().getSettlements().getBankId());
        return new ObjectMapper().convertValue(innerResponse, ProductConfigResponseTO.class);
    }

    @Override
    public ResponseDTO updateProductConfiguration(ProductConfigTO productConfig) throws Exception {
        String reqUrl = "https://api.razorpay.com/v2/accounts/" + productConfig.getAccount_id() + "/products/" + productConfig.getId();
        ProductConfigResponseTO productResponse = new ProductConfigResponseTO();
        ResponseDTO response = new ResponseDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setHeader();
            Object productTO = new Object();
            Map<String, Object> map = handleProductUpdate(productConfig);
            String stringDto = mapper.writeValueAsString(map);
            HttpEntity<String> entity = new HttpEntity<>(stringDto, this.RAZORPAY_HEADERS);
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            productResponse = restTemplateInstance.exchange(reqUrl, HttpMethod.PATCH, entity, ProductConfigResponseTO.class).getBody();
            if (CommonUtil.checkNullEmpty(productResponse)) {
                ProductConfigBO productConfigDao= this.productConfigDao.getProductConfig(productConfig.getAccount_id());
                productConfigDao.setTnc_accepted(productResponse.getTnc().isAccepted());
                if (CommonUtil.checkNullEmpty(productResponse.getActive_configuration()) && CommonUtil.checkNullEmpty(productResponse.getActive_configuration().getSettlements())) {
                    productConfigDao.setActive_configuration(new ActiveConfigurationBO());
                    productConfigDao.getActive_configuration().setSettlements(new SettlementsBO());
                    productConfigDao.getActive_configuration().getSettlements().setBankId(productConfig.getBankId());
                    productConfigDao.setProduct_name(productResponse.getProduct_name());
                    productConfigDao.setActivation_status(productResponse.getActivation_status());
                }
                this.productConfigDao.updateProductConfig(productConfigDao);
            }
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            productResponse = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), ProductConfigResponseTO.class);
        }
        if (productResponse != null) {
            if (productResponse.getError() != null
                    && productResponse.getError().getCode() != null
                    && !productResponse.getError().getCode().equals("NA")
                    && productResponse.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(productResponse.getError().getDescription());
            } else {
                response.setStatus(200);
                response.putData("productConfig", productResponse);
                response.setMessage("productResponse updated Successfully");
            }
        }
        return response;
    }

    @Override
    public ResponseDTO getSubMerchantList() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(200);
        responseDTO.putData("submerchant", this.accountDao.getSubMerchantList());
        return responseDTO;
    }

    public Map<String, Object> handleProductUpdate(ProductConfigTO productConfig) {
        Map<String, Object> map = new HashMap<>();
        if (CommonUtil.checkNullEmpty(productConfig.getActive_configuration().getSettlements())) {
            map.put("settlements", productConfig.getActive_configuration().getSettlements());
        }
        return map;
    }

    public Map<String, Object> handleAccountData(AccountDto accountDto) {
        Map<String, Object> map = new HashMap<>();
        if (CommonUtil.checkNullEmpty(accountDto.getLegal_info())) {
            Map<String, Object> legalInfo = new HashMap<>();
            if (CommonUtil.checkNullEmpty(accountDto.getLegal_info().getGst())) {
                legalInfo.put("gst", accountDto.getLegal_info().getGst());
            }
            if (CommonUtil.checkNullEmpty(accountDto.getLegal_info().getPan())) {
                legalInfo.put("pan", accountDto.getLegal_info().getPan());

            }
            if (CommonUtil.checkNullEmpty(accountDto.getLegal_info().getCin())) {
                legalInfo.put("cin", accountDto.getLegal_info().getCin());
            }
            map.put("legal_info", legalInfo);
        }
        if (CommonUtil.checkNullEmpty(accountDto.getProfile())) {
            map.put("profile", accountDto.getProfile());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getEmail())) {
            map.put("email", accountDto.getEmail());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getPhone())) {
            map.put("phone", accountDto.getPhone());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getLegal_business_name())) {
            map.put("legal_business_name", accountDto.getLegal_business_name());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getBusiness_type())) {
            map.put("business_type", accountDto.getBusiness_type());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getCustomer_facing_business_name())) {
            map.put("customer_facing_business_name", accountDto.getCustomer_facing_business_name());
        }
        if (CommonUtil.checkNullEmpty(accountDto.getContact_name())) {
            map.put("contact_name", accountDto.getContact_name());
        }
        return map;
    }

    public void handleDocumentsAddUpdate(DocumentResponseTO innerResponse, String subMerchantId, String stakeholderId, String url,String docType) {
        if (CommonUtil.checkNullEmpty(innerResponse.getBusiness_proof_of_identification())) {
            for (int i = 0; i < innerResponse.getBusiness_proof_of_identification().size(); i++) {
                DocumentBO document = new DocumentBO();
                document.setUrl(innerResponse.getBusiness_proof_of_identification().get(i).getUrl());
                document.setType(innerResponse.getBusiness_proof_of_identification().get(i).getType());
                document.setSubmerchant_id(subMerchantId);
                document.setStakeholder_id(stakeholderId);
                if (url.contains("stakeholders")) {
                    document.setParent("stakeholders");
                } else {
                    document.setParent("submerchant");
                }
                if(docType.equals(document.getType())){
                    this.accountDao.addDocument(document);
                }
            }
        }
        if (CommonUtil.checkNullEmpty(innerResponse.getIndividual_proof_of_address())) {
            for (int i = 0; i < innerResponse.getIndividual_proof_of_address().size(); i++) {
                DocumentBO document = new DocumentBO();
                document.setUrl(innerResponse.getIndividual_proof_of_address().get(i).getUrl());
                document.setType(innerResponse.getIndividual_proof_of_address().get(i).getType());
                document.setSubmerchant_id(subMerchantId);
                document.setStakeholder_id(stakeholderId);
                if (url.contains("stakeholders")) {
                    document.setParent("stakeholders");
                } else {
                    document.setParent("submerchant");
                }
                if(docType.equals(innerResponse.getIndividual_proof_of_address().get(i).getType())){
                    this.accountDao.addDocument(document);
                }
            }
        }
        if (CommonUtil.checkNullEmpty(innerResponse.getIndividual_proof_of_identification())) {
            for (int i = 0; i < innerResponse.getIndividual_proof_of_identification().size(); i++) {
                DocumentBO document = new DocumentBO();
                document.setUrl(innerResponse.getIndividual_proof_of_identification().get(i).getUrl());
                document.setType(innerResponse.getIndividual_proof_of_identification().get(i).getType());
                document.setSubmerchant_id(subMerchantId);
                document.setStakeholder_id(stakeholderId);
                if (url.contains("stakeholders")) {
                    document.setParent("stakeholders");
                } else {
                    document.setParent("submerchant");
                }
                if(docType.equals(innerResponse.getIndividual_proof_of_identification().get(i).getType())){
                    this.accountDao.addDocument(document);
                }
            }
        }
        if (CommonUtil.checkNullEmpty(innerResponse.getAdditional_documents())) {
            for (int i = 0; i < innerResponse.getAdditional_documents().size(); i++) {
                DocumentBO document = new DocumentBO();
                document.setUrl(innerResponse.getAdditional_documents().get(i).getUrl());
                document.setType(innerResponse.getAdditional_documents().get(i).getType());
                document.setSubmerchant_id(subMerchantId);
                document.setStakeholder_id(stakeholderId);
                if (url.contains("stakeholders")) {
                    document.setParent("stakeholders");
                } else {
                    document.setParent("submerchant");
                }
                if(docType.equals(document.getType())){
                    this.accountDao.addDocument(document);
                }
            }
        }
    }

    public SubMerchantBO convertToBO(SubMerchantTO subMerchantTO) {
        SubMerchantBO subMerchantBO = modelMapper.map(subMerchantTO, SubMerchantBO.class);
        return subMerchantBO;
    }

    public SubMerchantTO updateSubMerchant(SubMerchantTO subMerchantTO) {
        SubMerchantBO subMerchant = this.accountDao.updateSubMerchant(modelMapper.map(subMerchantTO, SubMerchantBO.class));
        return modelMapper.map(subMerchant, SubMerchantTO.class);
    }

}
