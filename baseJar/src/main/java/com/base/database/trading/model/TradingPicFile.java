package com.base.database.trading.model;

public class TradingPicFile {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pic_file.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pic_file.pic_url
     *
     * @mbggenerated
     */
    private String picUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pic_file.md5_str
     *
     * @mbggenerated
     */
    private String md5Str;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pic_file.id
     *
     * @return the value of trading_pic_file.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pic_file.id
     *
     * @param id the value for trading_pic_file.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pic_file.pic_url
     *
     * @return the value of trading_pic_file.pic_url
     *
     * @mbggenerated
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pic_file.pic_url
     *
     * @param picUrl the value for trading_pic_file.pic_url
     *
     * @mbggenerated
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pic_file.md5_str
     *
     * @return the value of trading_pic_file.md5_str
     *
     * @mbggenerated
     */
    public String getMd5Str() {
        return md5Str;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pic_file.md5_str
     *
     * @param md5Str the value for trading_pic_file.md5_str
     *
     * @mbggenerated
     */
    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str == null ? null : md5Str.trim();
    }
}