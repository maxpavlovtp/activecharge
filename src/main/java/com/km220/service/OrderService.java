package com.km220.service;

import com.km220.dao.order.OrderRepository;
import com.km220.dao.station.StationRepository;
import com.km220.service.job.ChargerService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

	@Value("${monobank.token}")
	private String monoToken;

	@Value("${monobank.callBackHost}")
	private String callBackHost;

	private final StationRepository stationRepository;
	private final OrderRepository orderRepository;
	private final ChargerService chargerService;

	public String initOrder(String stationNumber, Integer hours) throws IOException {
		String checkoutLink = null;

		URL url = new URL("https://api.monobank.ua/api/merchant/invoice/create");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("X-Token", monoToken);

		String uahPerHour = stationRepository.getByNumber(stationNumber).getCostPerHour();
		String uahCents = String.valueOf(hours * Integer.valueOf(uahPerHour) * 100);
		String body = "{\n"
				+ "    \"amount\": " + uahCents + ",\n"
				+ "    \"ccy\": 980,\n"
				+ "    \"merchantPaymInfo\": {\n"
				+ "        \"reference\": \"84d0070ee4e44667b31371d8f8813947\",\n"
				+ "        \"destination\": \"" + hours + " годин зарядки\",\n"
				+ "        \"basketOrder\": []\n"
				+ "    },\n"
				+ "    \"redirectUrl\": \"http://" + callBackHost + "/charging?stationNumber="
				+ stationNumber + "\",\n"
				+ "    \"webHookUrl\": \"http://" + callBackHost + ":8080/order/callBackMono\",\n"
				+ "    \"validity\": 3600,\n"
				+ "    \"paymentType\": \"debit\"\n"
				+ "}";

		byte[] out = body.getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		try (OutputStream os = http.getOutputStream()) {
			log.info("sending generateCheckoutLink request to monobank: {}", body);
			os.write(out);
		}

		BufferedReader br = null;
		if (http.getResponseCode() == 200) {
			br = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String strCurrentLine;
			while ((strCurrentLine = br.readLine()) != null) {
				log.info("Checkout info: {}", strCurrentLine);
				checkoutLink = strCurrentLine;
			}
		} else {
			br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
			String strCurrentLine;
			while ((strCurrentLine = br.readLine()) != null) {
				log.error("Monobank error: {}", strCurrentLine);
			}
		}

		String invoiceId = fetchInvoiceId(checkoutLink);
		orderRepository.add(invoiceId, stationNumber);

		return checkoutLink;
	}
	public void processOrder(String callBackMono) {
		log.info("Call back from monobank: {}", callBackMono);
		// todo move to service
		if (callBackMono.contains("\"status\":\"success\"")) {
			String invoiceId = fetchInvoiceId(callBackMono);
			log.info("invoiceId: {}", invoiceId);
//			String stationNumberFromCache = invoiceCache.get(invoiceId).split(";")[0];
//			String hours = invoiceCache.get(invoiceId).split(";")[1];
//			log.info("stationNumberFromCache: {}", stationNumberFromCache);
			chargerService.start("1", Integer.parseInt("6") * 3600);
		}
	}

	public String fetchInvoiceId(String monoResponse) {
		return monoResponse.replace("{\"invoiceId\":\"", "").split("\"")[0];
	}

	private UUID createOrder(String invoiceId, String stationNumber) {
		return orderRepository.add(invoiceId, stationNumber);
	}
}
