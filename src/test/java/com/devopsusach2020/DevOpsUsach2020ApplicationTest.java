package com.devopsusach2020;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.devopsusach2020.model.RespuestaAPICovid;
import com.devopsusach2020.model.Pais;
import com.devopsusach2020.rest.RestData;

@SpringBootTest
public class DevOpsUsach2020ApplicationTest {
	RestData controller = new RestData();

	@Test
	public void testValidaApiCovid() throws Exception {
		int expectedStatus = 200;
		RespuestaAPICovid response = controller.validaApiCovid();

		assertEquals(expectedStatus, response.getStatusCode());
	}

	@Test
	public void testEstadoPais() throws Exception {

		String expectedMessage = "ok";
		Pais response = controller.getTotalPais("Ecuador");
		assertEquals(expectedMessage, response.getMensaje());

	}

}
