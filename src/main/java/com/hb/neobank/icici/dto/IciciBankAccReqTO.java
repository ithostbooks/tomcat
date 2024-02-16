package com.hb.neobank.icici.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IciciBankAccReqTO {
	private String user;
	private String passCode;
	private String mobileNo;
	private String emailId;
	private String p_CATEGORY_TYPE;
	private IciciBankAccOpeningData data;

	public IciciBankAccReqTO() {
		this.data = new IciciBankAccOpeningData();
	}
}

@Getter
@Setter
class IciciBankAccOpeningData {
	private String p_PANNO;
	private String p_PANSTATE;
	private String p_SALUTATION;
	private String p_FNAME;
	private String p_MNAME;
	private String p_LNAME;
	private String p_DOB;
	private String p_IICM_GENDER;
	private String p_IICM_MARITAL_STATUS;
	private String p_MOTHER_NAME;
	private String p_IICM_FATHERS_NAME;
	private String p_ID_PROOF;
	private String p_ID_PRF_NUM;
	private String p_ADD_PRF;
	private String p_ADDR_PROOF_NUM;
	private String p_COMMADD1;
	private String p_COMMADD2;
	private String p_COMMADD3;
	private String p_COMMSTREETNUM;
	private String p_COMMSTREETNAME;
	private String p_COMMLOCALITY;
	private String p_COMMLANDMARK;
	private String p_COMMCITY;
	private String p_COMMSTATE;
	private String p_COMMCOUNTRY;
	private String p_COMMPCODE;
	private String p_FATCA_BIRTH_COUNTRY;
	private String p_FATCA_COUTRY_OF_CITYZEN;
	private String p_RESI_COUNTRY_FORTAX;
	private String p_US_PERSON;
}
