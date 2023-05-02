package com.developerjini.service;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleServiceTests {
	@Autowired
	private SampleService service;
	
	@Test
	public void testExist() {
		assertNotNull(service);
	}
	
	@Test
	public void testAddData() throws Exception {
		String data = "나무 심기를 통하여 국민의 나무 사랑 정신을 북돋우고, 산지(山地)의 자원화를 위하여 제정된 날.";
		byte[] bs = data.getBytes("utf-8");
		log.info(bs.length);
		byte[] bs2 = new byte[50];
		System.arraycopy(bs, 0, bs2, 0, 50);
		log.info(bs2.length);
		String str = new String(bs2, "utf-8");
		log.info(str);
//		log.info(data.length());
//		data = "abcd";
//		service.addData(data);
	}
}
