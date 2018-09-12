package com.shangde.mobile;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * (这里用一句话描述这个类的作用)
 *
 * @author: Fan Beibei
 * @date: 2018/4/9 16:47
 * @Modified By:
 */
public class TestParam {
	String version ="1.0";

    @Test
    public void testEncodeParam(){
        

        String data0="{" + 
        		"    \"employeeId\": \"71350\"," + 
        		"    \"pushEvidence\": \"UdkqifCrhge/89DHwWuIcGfyWe8y7QzMT41AXiG4488=\"," + 
        		"    \"os\": \"android\"," + 
        		"    \"type\": \"1\"" + 
        		"}";
		String data = (String) SecurityUtil.getEncryptDataForParam(data0,version);
        System.out.println(data);
        String token = DigestUtils.md5Hex(data);
        System.out.println(token);


        String s = SecurityUtil
                .getDecryptDataForParam(data, version);
        System.out.println(s);


        System.out.println("_-------------------------------------------------------------------------");


        

    }
    
    @Test
    public void testDecode() {
    	
    	String s1 = "kOcqW-qVQpNLpxNYcwcb2WiWLtBFTs32tRcRZ9TQQg-Kfb3nirTml86vUmtOhw3iW8ptDmj3fJBef2WU8l5WnY-PqGDBGnKiiaVVLPHmzw9LR2fCaVy39Zk2uMZ9y9RL-72gJp72jh9g7u2t1r5zT8gkavTGdrvR9isWrRCvEjwk-RFTypQFWpGZ5NlPZ1i-7_Ac8Up1FgWkarOAAfaMjW1kkI2sFD4sa7fKiVxo93CWjGf468fz1Mxa4OpTG8jcZBFEiUAUIC71YIoAJOo7l7jFlLRkPFG0vmueyTi0YtXui4mpzGepnHtyfc2LMJvRT7xVsEs46SvosV42_kUBkl0DQ6-6Pcn53BClbMYm2LmDizAyWZxy6FByDocYO2q3kHoJz9UPRF-ZxN6tDPHDOyKI83Ai4kScAOhuPQ0_YABSKLQwdprvKGCBrJ7rfkp7lAtgHJNUoc6xF0rwBMjvaFiHdvo4lxEsWqwYwBNw5KLkLb2h0d0P8aKiX2SnTaqUlPI1kcKexf7ezaVTUwUAa5VTJs3O89Rlbp79QZkn1I75rGnFd3-m10oojoUTBcpUefcUYbuNzBk9oHudIb4KsfEGCw58_bKijQXLF_yWkrzl3ApJJeUISb_lydtTMY4tHa14qph-WiThMNjPWIWUa6XPAlPRXrcAG9h3x_TKc0DPi0t8w-TXh2Ab6OSal_EWZeDKjW4l5AKg7ETXks3MpcBHh-G-QHGqC8k9FTej1UoXjVmHzcEhZ3GtyY2z_nKSJbQ-gZ0uy6Tukdtzx_JOwVZ4-wtHqdnOL1Y7ITQenilmYKokuuu-MRMJ2tBUK9hsD-UhJhPxyhxahk03QWMSFWqt6tm2Kh3W57fOJuOq__yS6BkpzrsnXhdFeD8xqpEk5UgwMqD_CjoBzZRWTlsbgwrGnT1X3K86Z4N3hyktrxW6Tq-pxi214UUhjyICPYcKKtcjGvTPjb4drkbu3RN7Ae_3paAxCtW0daofA-xQSK0isz-KucABa5Y_IjPC3nJ7YQeOasOwNRHHzUABo9an6JyQZiCuf9b1zNd-5h7ZKZKs2Q6L9DnhNIzcZjkgdSzixi-O2XgAaNb26j7n-6zrvLuGJmCPdcrsu03M0rB4h2GUM5UJN5dq3hHjq19XS73WWpdTBtGQJpJIVZcU909hERfez9-56bgGeoedYIjWguHOBSPiFJjG0R5pFgd9lad-Svcjcd6pYASg1VessoHq0dIB8WkpTVNwWMM4xDj_34N0oYqujKKMrmE7HG75a-VxtHTr27RR6_cZCTRsQNTZznmGuiYVdppIoiR5Kh6ljfx-M0A-9u8TFkGq76JtXN28iDjwyvcddqKsRiO5r6F--o0DJTJwC63fa8hvprN6ED_38FEuX7mbe_FJlyzAuBfg6AvXzJEbB6oU0CGZ-RAmGcOUBjuD19OKzNvSdSoh6EzCe__npA2y24CTv05Q0Ot_nvcaDqPcwl_btGjuFH4pstnHQ3eCKeCC7l2NFo6XTx8tWtmNRS_9DFQAkkYw6FbFK1LL8ggGcHUOVphMwBQrn-t-RqAdNA7wNHLKg4vCYUtRbQmxpwabx9AcUTqjRfdh8-BfJ6uHTPU1FVMRyI1SEH_j3tzxH59oNTQqmfUaet-uoHBhk3zDCXxyY_GuINeYxNZPUEssYkuK2g6CagvYkjaXUN6uVajNLcAQryseuAAJUZSxKKb-yLgAHADqOvvywJDKb4xou5HVRRkeyYO1h97GWdpCEFytNKNMQkGex0jOmpcNVSbj2zk9ycOsNMNUd_6Ek6TURH_O6YNx2npJ8amAapsP58IpJYdybC2pFMWApMw9kkoABFupIBzWL8ceiGVfxF8LHOz9fT8WELC3S9mnBaW7HPzNApQK9f71JCKCYvlPI_XO_ThdJxOr_HoPgqkeAN1P211uFCq2CKu0mKydI8bog-uEbuwG0ME2BFLgc3qeXbm2LqcbxhpkwEAOOtrCEDGQMA-rKjni4slOSih-C18E--53oK1KZap8uHBjDifg43uxOAbdLXNvkdGLXEs3o-d1EM6fZfZ9YomaQbgoMobQFtkcvXeKcJ02Cz53hL5vsL22mLtQ65j0bjX6" ;

        System.out.println(SecurityUtil
                .getDecryptDataForParam(s1, version));
    }
    
    

}
