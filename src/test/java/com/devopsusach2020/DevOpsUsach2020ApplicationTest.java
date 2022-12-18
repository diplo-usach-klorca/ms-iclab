package com.devopsusach2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsusach2020.model.Mundial;
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
	public void testEstadoMundial() throws Exception {	

		int expectedDeadths = 6658989;
		Mundial response = controller.getTotalMundial();
		assertTrue( expectedDeadths >= response.getTotalDeaths());

	}

}
