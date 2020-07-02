package in.samco;

import io.swagger.client.ApiException;
import io.swagger.client.api.OrdersApi;
import io.swagger.client.api.QuoteApi;
import io.swagger.client.api.UserLoginApi;
import io.swagger.client.model.LoginRequest;
import io.swagger.client.model.LoginResponse;
import io.swagger.client.model.MarketDepthResponse;
import io.swagger.client.model.OrderRequest;
import io.swagger.client.model.OrderResponse;

public class Sample {

	public static void main(String[] args) {

		try {

			UserLoginApi userLoginApi = new UserLoginApi();
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUserId("user_id");
			loginRequest.setPassword("password");
			loginRequest.setYob("yob");
			LoginResponse loginResponse = userLoginApi.login(loginRequest);
			String session = loginResponse.getSessionToken();
			System.out.println("session : " + loginResponse.getSessionToken());

			if (loginResponse.getSessionToken() != null && !"".equalsIgnoreCase(loginResponse.getSessionToken())) {
				System.out.println("session " + session);

				QuoteApi quoteApi = new QuoteApi();
				MarketDepthResponse quote = quoteApi.getQuote(loginResponse.getSessionToken(), "SBIN", "NSE");

				if ("Success".equalsIgnoreCase(quote.getStatus())) {

					OrdersApi ordersApi = new OrdersApi();
					OrderRequest orderRequest = new OrderRequest();
					orderRequest.setSymbolName("RELIANCE");
					orderRequest.setExchange("BSE");
					orderRequest.setTransactionType("BUY");
					orderRequest.setOrderType("MKT");
					orderRequest.setQuantity("2");
					orderRequest.setDisclosedQuantity("");
					orderRequest.setOrderValidity("DAY");
					orderRequest.setProductType("MIS");
					orderRequest.setAfterMarketOrderFlag("NO");
					OrderResponse placeOrder = ordersApi.placeOrder(loginResponse.getSessionToken(), orderRequest);
					System.out.println("place order success response : " + placeOrder);

				}
			}

		} catch (ApiException ex) {
			System.out.println("Exception caught while fetching trade api : " + ex.getResponseBody());
		}

	}
}
