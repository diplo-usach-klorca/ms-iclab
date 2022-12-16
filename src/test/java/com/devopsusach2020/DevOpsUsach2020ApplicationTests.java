package com.devopsusach2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsusach2020.model.Pais;
import com.devopsusach2020.model.RespuestaAPICovid;
import com.devopsusach2020.rest.RestData;

@SpringBootTest
class DevOpsUsach2020ApplicationTests {

	RestData controller = new RestData();

	@Test 
	public void testValidaApiCovid() throws Exception {
		int expectedStatus = 200;
		RespuestaAPICovid response = controller.validaApiCovid();
		assertEquals(expectedStatus, response.getStatusCode());
	}

	@Test
	public void testEstadoPais() throws Exception {	

		String pais = "Ecuador";
		Pais response = controller.getTotalPais(pais);
		assertEquals( pais.toLowerCase(), response.getCountry().toLowerCase());

	}

}
