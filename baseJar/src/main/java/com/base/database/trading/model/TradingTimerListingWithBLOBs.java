package com.base.database.trading.model;

public class TradingTimerListingWithBLOBs extends TradingTimerListing {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_timer_listing.timer_message
     *
     * @mbggenerated
     */
    private String timerMessage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_timer_listing.return_message
     *
     * @mbggenerated
     */
    private String returnMessage;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_timer_listing.timer_message
     *
     * @return the value of trading_timer_listing.timer_message
     *
     * @mbggenerated
     */
    public String getTimerMessage() {
        return timerMessage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_timer_listing.timer_message
     *
     * @param timerMessage the value for trading_timer_listing.timer_message
     *
     * @mbggenerated
     */
    public void setTimerMessage(String timerMessage) {
        this.timerMessage = timerMessage == null ? null : timerMessage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_timer_listing.return_message
     *
     * @return the value of trading_timer_listing.return_message
     *
     * @mbggenerated
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_timer_listing.return_message
     *
     * @param returnMessage the value for trading_timer_listing.return_message
     *
     * @mbggenerated
     */
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage == null ? null : returnMessage.trim();
    }
}