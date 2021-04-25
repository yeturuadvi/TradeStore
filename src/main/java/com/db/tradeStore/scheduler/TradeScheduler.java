package com.db.tradeStore.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.db.tradeStore.service.TradeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Advitha
 * 
 * Batch job to validate and updates the expire flag  of a Trade
 *
 */
@Slf4j
@Component
public class TradeScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(TradeScheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	TradeService tradeService;

	/**
	 * For Testing purpose scheduled the job for 30mins ideally it should trigger once in a day
	 * */
	@Scheduled(cron = "${trade.expiryDate.schedule}")
	public void reportCurrentTime() {
		log.info("Triggering the TradeScheduler Job to validate and update the expiridity of a Trade {}", dateFormat.format(new Date()));
		tradeService.updateTradeExpiryFlag();
	}

}
