package com.flynas.worker.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flynas.worker.response.SampleResponse;
import com.flynas.worker.utils.CommonUtility;

@Component
public class SampleConversionUtil {

	@Autowired
	private CommonUtility commonUtility;

 
	public SampleResponse getSampleResponse() {

		SampleResponse sampleResponse = new SampleResponse();
		sampleResponse.setName("Test User");
		sampleResponse.setAge("22");
		return sampleResponse;

	}

}
