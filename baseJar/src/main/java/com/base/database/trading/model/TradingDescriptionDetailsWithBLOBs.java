package com.base.database.trading.model;

public class TradingDescriptionDetailsWithBLOBs extends TradingDescriptionDetails {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_description_details.pay_info
     *
     * @mbggenerated
     */
    private String payInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_description_details.shipping_info
     *
     * @mbggenerated
     */
    private String shippingInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_description_details.contact_info
     *
     * @mbggenerated
     */
    private String contactInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_description_details.Guarantee_info
     *
     * @mbggenerated
     */
    private String guaranteeInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_description_details.Feedback_info
     *
     * @mbggenerated
     */
    private String feedbackInfo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_description_details.pay_info
     *
     * @return the value of trading_description_details.pay_info
     *
     * @mbggenerated
     */
    public String getPayInfo() {
        return payInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_description_details.pay_info
     *
     * @param payInfo the value for trading_description_details.pay_info
     *
     * @mbggenerated
     */
    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo == null ? null : payInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_description_details.shipping_info
     *
     * @return the value of trading_description_details.shipping_info
     *
     * @mbggenerated
     */
    public String getShippingInfo() {
        return shippingInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_description_details.shipping_info
     *
     * @param shippingInfo the value for trading_description_details.shipping_info
     *
     * @mbggenerated
     */
    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo == null ? null : shippingInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_description_details.contact_info
     *
     * @return the value of trading_description_details.contact_info
     *
     * @mbggenerated
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_description_details.contact_info
     *
     * @param contactInfo the value for trading_description_details.contact_info
     *
     * @mbggenerated
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_description_details.Guarantee_info
     *
     * @return the value of trading_description_details.Guarantee_info
     *
     * @mbggenerated
     */
    public String getGuaranteeInfo() {
        return guaranteeInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_description_details.Guarantee_info
     *
     * @param guaranteeInfo the value for trading_description_details.Guarantee_info
     *
     * @mbggenerated
     */
    public void setGuaranteeInfo(String guaranteeInfo) {
        this.guaranteeInfo = guaranteeInfo == null ? null : guaranteeInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_description_details.Feedback_info
     *
     * @return the value of trading_description_details.Feedback_info
     *
     * @mbggenerated
     */
    public String getFeedbackInfo() {
        return feedbackInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_description_details.Feedback_info
     *
     * @param feedbackInfo the value for trading_description_details.Feedback_info
     *
     * @mbggenerated
     */
    public void setFeedbackInfo(String feedbackInfo) {
        this.feedbackInfo = feedbackInfo == null ? null : feedbackInfo.trim();
    }
}