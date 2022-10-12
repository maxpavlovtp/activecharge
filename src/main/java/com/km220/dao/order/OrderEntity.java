package com.km220.dao.order;

import com.km220.dao.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class OrderEntity extends DatabaseEntity {

  public static final String INVOICE_ID = "invoice_id";
  public static final String STATE = "state";
  public static final String station_number = "station_number";
	public static final String PERIOD_SEC = "period_sec";

  private String invoiceId;
  private OrderState state;
	private String stationNumber;
	private int periodSec;
}
