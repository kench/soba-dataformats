package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * DynamoDB item for the BarcodeScans table, keyed by Bevy event ID and the last five digits of the
 * Bevy ticket ID.
 *
 * <p>The barcode is the raw value read from the ticket QR code, in the form {@code
 * event-id:last-5-digits-of-ticket-id}, for example {@code 1048:77047}. The scan time is the Unix
 * epoch in seconds, matching the other SOBA tables.
 */
@DynamoDbBean
public class BarcodeScan {
  private Integer eventId;
  private Integer ticketId;
  private String barcode;
  private Long scanTime;

  @DynamoDbPartitionKey
  @DynamoDbAttribute("event_id")
  public Integer getEventId() {
    return eventId;
  }

  public void setEventId(final Integer eventId) {
    this.eventId = eventId;
  }

  @DynamoDbSortKey
  @DynamoDbAttribute("ticket_id")
  public Integer getTicketId() {
    return ticketId;
  }

  public void setTicketId(final Integer ticketId) {
    this.ticketId = ticketId;
  }

  @DynamoDbAttribute("barcode")
  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(final String barcode) {
    this.barcode = barcode;
  }

  @DynamoDbAttribute("scan_time")
  public Long getScanTime() {
    return scanTime;
  }

  public void setScanTime(final Long scanTime) {
    this.scanTime = scanTime;
  }
}
