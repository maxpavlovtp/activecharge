package com.km220.dao.order;

import com.km220.dao.DatabaseEntity;
import com.km220.dao.job.ChargingJobState;
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

  private String invoiceId;
  private OrderState state;

}
