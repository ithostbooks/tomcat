package com.hb.neobank.razorpay.controller;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderRequestDto;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderResponseDto;
import com.hb.neobank.razorpay.service.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2")
public class StakeholderApiController {

    @Autowired
    private StakeholderService stakeholderService;

    private ResponseDTO responseDTO;


    @RequestMapping(value = "/accounts/{account_id}/stakeholders", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addStakeholder(@RequestBody StakeholderRequestDto stakeholderRequestDto,
                                                 @PathVariable("account_id")  String accountId) throws Exception {
        ResponseDTO stakeholderResponse= stakeholderService.addStakeholder(stakeholderRequestDto,accountId);
//        responseDTO = ResponseDTO.responseBuilder(200, "stakeHolder"," stakeholder","/stakeholder", "project", stakeholderResponse);
        return new ResponseEntity<>(stakeholderResponse, HttpStatus.OK);

    }

    @RequestMapping(value = "/accounts/{account_id}/stakeholders/{stakeholder_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStakeholderById(@PathVariable("account_id") String  accountId,@PathVariable("stakeholder_id") String stakeId) throws Exception {
        return new ResponseEntity<>(stakeholderService.getStakeholderById(accountId,stakeId), HttpStatus.OK);
    }


    @RequestMapping(value = "/accounts/{account_id}/stakeholders", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStakeholders(@PathVariable("account_id") String  accountId) throws Exception {
        return new ResponseEntity<>(stakeholderService.getAllStakeholders(accountId), HttpStatus.OK);
    }


    @RequestMapping(value = "/accounts/{account_id}/stakeholders/{stakeholder_id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStakeholderById(@RequestBody StakeholderRequestDto stakeholderRequestDto,
                     @PathVariable("account_id") String accountId,@PathVariable("stakeholder_id") String stakeId) throws Exception {
        return new ResponseEntity<>(stakeholderService.updateStakeholder(stakeholderRequestDto,accountId,stakeId), HttpStatus.OK);
    }

}
