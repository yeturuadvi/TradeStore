package com.db.tradeStore;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.db.tradeStore.controller.TradeController;
import com.db.tradeStore.domain.Trade;
import com.db.tradeStore.exception.InvalidTradeException;

@SpringBootTest
class TradeStoreApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private TradeController tradeController;

	@Test
	void testValidateAndStoreTrade_successful() {
		ResponseEntity responseEntity = tradeController.validateStoreTrade(createTrade("T1",1,"B1","CP-1",LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1",tradeList.get(0).getTradeId());
	}

	@Test
	void testValidateAndStoreTradeWhenMaturityDatePast() {
		LocalDate localDate = getLocalDate(2021, 03, 21);
		ResponseEntity responseEntity = tradeController.validateStoreTrade(createTrade("T2", 1,"B1","CP-1", localDate));
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}

	@Test
	void testValidateAndStoreTradeWhenNewVersion() {
		// step-1 create trade T1 with new Version
		ResponseEntity responseEntity = tradeController.validateStoreTrade(createTrade("T1",2,"B1", "CP-2",LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1",tradeList.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList.get(0).getVersion());
		Assertions.assertEquals("B1",tradeList.get(0).getBookId());

		//step-2 create trade T1 with old version
		ResponseEntity responseEntity1 = tradeController.validateStoreTrade(createTrade("T1", 1, "B1", "CP-2", LocalDate.now()));
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

	}

	@Test
	void testValidateAndStoreTradeWhenSameVersionTrade(){
		ResponseEntity responseEntity = tradeController.validateStoreTrade(createTrade("T1",2,"B2", "CP-1", LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1",tradeList.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList.get(0).getVersion());
		Assertions.assertEquals("B2",tradeList.get(0).getBookId());

		//step-2 create trade with same version
		Trade trade2 = createTrade("T1",2,"B1","CP-2", LocalDate.now());
		trade2.setBookId("B2");
		ResponseEntity responseEntity2 = tradeController.validateStoreTrade(trade2);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity2);
		List<Trade> tradeList2 =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList2.size());
		Assertions.assertEquals("T1",tradeList2.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList2.get(0).getVersion());
		Assertions.assertEquals("B2",tradeList2.get(0).getBookId());

	}
	private Trade createTrade(String tradeId,int version, String bookId, String counterParty, LocalDate  maturityDate){
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(bookId);
		trade.setVersion(version);
		trade.setCounterParty(counterParty);
		trade.setMaturityDate(maturityDate);
		trade.setExpired("N");
		return trade;
	}

	public static LocalDate getLocalDate(int year,int month, int day){
		LocalDate localDate = LocalDate.of(year,month,day);
		return localDate;
	}


}
