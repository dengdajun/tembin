package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingSellerprofilesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public TradingSellerprofilesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidIsNull() {
            addCriterion("PaymentProfileID is null");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidIsNotNull() {
            addCriterion("PaymentProfileID is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidEqualTo(Long value) {
            addCriterion("PaymentProfileID =", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidNotEqualTo(Long value) {
            addCriterion("PaymentProfileID <>", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidGreaterThan(Long value) {
            addCriterion("PaymentProfileID >", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidGreaterThanOrEqualTo(Long value) {
            addCriterion("PaymentProfileID >=", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidLessThan(Long value) {
            addCriterion("PaymentProfileID <", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidLessThanOrEqualTo(Long value) {
            addCriterion("PaymentProfileID <=", value, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidIn(List<Long> values) {
            addCriterion("PaymentProfileID in", values, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidNotIn(List<Long> values) {
            addCriterion("PaymentProfileID not in", values, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidBetween(Long value1, Long value2) {
            addCriterion("PaymentProfileID between", value1, value2, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofileidNotBetween(Long value1, Long value2) {
            addCriterion("PaymentProfileID not between", value1, value2, "paymentprofileid");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameIsNull() {
            addCriterion("PaymentProfileName is null");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameIsNotNull() {
            addCriterion("PaymentProfileName is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameEqualTo(String value) {
            addCriterion("PaymentProfileName =", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameNotEqualTo(String value) {
            addCriterion("PaymentProfileName <>", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameGreaterThan(String value) {
            addCriterion("PaymentProfileName >", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameGreaterThanOrEqualTo(String value) {
            addCriterion("PaymentProfileName >=", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameLessThan(String value) {
            addCriterion("PaymentProfileName <", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameLessThanOrEqualTo(String value) {
            addCriterion("PaymentProfileName <=", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameLike(String value) {
            addCriterion("PaymentProfileName like", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameNotLike(String value) {
            addCriterion("PaymentProfileName not like", value, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameIn(List<String> values) {
            addCriterion("PaymentProfileName in", values, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameNotIn(List<String> values) {
            addCriterion("PaymentProfileName not in", values, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameBetween(String value1, String value2) {
            addCriterion("PaymentProfileName between", value1, value2, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andPaymentprofilenameNotBetween(String value1, String value2) {
            addCriterion("PaymentProfileName not between", value1, value2, "paymentprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidIsNull() {
            addCriterion("ReturnProfileID is null");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidIsNotNull() {
            addCriterion("ReturnProfileID is not null");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidEqualTo(Long value) {
            addCriterion("ReturnProfileID =", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidNotEqualTo(Long value) {
            addCriterion("ReturnProfileID <>", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidGreaterThan(Long value) {
            addCriterion("ReturnProfileID >", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidGreaterThanOrEqualTo(Long value) {
            addCriterion("ReturnProfileID >=", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidLessThan(Long value) {
            addCriterion("ReturnProfileID <", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidLessThanOrEqualTo(Long value) {
            addCriterion("ReturnProfileID <=", value, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidIn(List<Long> values) {
            addCriterion("ReturnProfileID in", values, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidNotIn(List<Long> values) {
            addCriterion("ReturnProfileID not in", values, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidBetween(Long value1, Long value2) {
            addCriterion("ReturnProfileID between", value1, value2, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofileidNotBetween(Long value1, Long value2) {
            addCriterion("ReturnProfileID not between", value1, value2, "returnprofileid");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameIsNull() {
            addCriterion("ReturnProfileName is null");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameIsNotNull() {
            addCriterion("ReturnProfileName is not null");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameEqualTo(String value) {
            addCriterion("ReturnProfileName =", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameNotEqualTo(String value) {
            addCriterion("ReturnProfileName <>", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameGreaterThan(String value) {
            addCriterion("ReturnProfileName >", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameGreaterThanOrEqualTo(String value) {
            addCriterion("ReturnProfileName >=", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameLessThan(String value) {
            addCriterion("ReturnProfileName <", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameLessThanOrEqualTo(String value) {
            addCriterion("ReturnProfileName <=", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameLike(String value) {
            addCriterion("ReturnProfileName like", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameNotLike(String value) {
            addCriterion("ReturnProfileName not like", value, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameIn(List<String> values) {
            addCriterion("ReturnProfileName in", values, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameNotIn(List<String> values) {
            addCriterion("ReturnProfileName not in", values, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameBetween(String value1, String value2) {
            addCriterion("ReturnProfileName between", value1, value2, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andReturnprofilenameNotBetween(String value1, String value2) {
            addCriterion("ReturnProfileName not between", value1, value2, "returnprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidIsNull() {
            addCriterion("ShippingProfileID is null");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidIsNotNull() {
            addCriterion("ShippingProfileID is not null");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidEqualTo(Long value) {
            addCriterion("ShippingProfileID =", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidNotEqualTo(Long value) {
            addCriterion("ShippingProfileID <>", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidGreaterThan(Long value) {
            addCriterion("ShippingProfileID >", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidGreaterThanOrEqualTo(Long value) {
            addCriterion("ShippingProfileID >=", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidLessThan(Long value) {
            addCriterion("ShippingProfileID <", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidLessThanOrEqualTo(Long value) {
            addCriterion("ShippingProfileID <=", value, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidIn(List<Long> values) {
            addCriterion("ShippingProfileID in", values, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidNotIn(List<Long> values) {
            addCriterion("ShippingProfileID not in", values, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidBetween(Long value1, Long value2) {
            addCriterion("ShippingProfileID between", value1, value2, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofileidNotBetween(Long value1, Long value2) {
            addCriterion("ShippingProfileID not between", value1, value2, "shippingprofileid");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameIsNull() {
            addCriterion("ShippingProfileName is null");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameIsNotNull() {
            addCriterion("ShippingProfileName is not null");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameEqualTo(String value) {
            addCriterion("ShippingProfileName =", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameNotEqualTo(String value) {
            addCriterion("ShippingProfileName <>", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameGreaterThan(String value) {
            addCriterion("ShippingProfileName >", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameGreaterThanOrEqualTo(String value) {
            addCriterion("ShippingProfileName >=", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameLessThan(String value) {
            addCriterion("ShippingProfileName <", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameLessThanOrEqualTo(String value) {
            addCriterion("ShippingProfileName <=", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameLike(String value) {
            addCriterion("ShippingProfileName like", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameNotLike(String value) {
            addCriterion("ShippingProfileName not like", value, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameIn(List<String> values) {
            addCriterion("ShippingProfileName in", values, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameNotIn(List<String> values) {
            addCriterion("ShippingProfileName not in", values, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameBetween(String value1, String value2) {
            addCriterion("ShippingProfileName between", value1, value2, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andShippingprofilenameNotBetween(String value1, String value2) {
            addCriterion("ShippingProfileName not between", value1, value2, "shippingprofilename");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Long> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Long> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentUuidIsNull() {
            addCriterion("parent_uuid is null");
            return (Criteria) this;
        }

        public Criteria andParentUuidIsNotNull() {
            addCriterion("parent_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andParentUuidEqualTo(String value) {
            addCriterion("parent_uuid =", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidNotEqualTo(String value) {
            addCriterion("parent_uuid <>", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidGreaterThan(String value) {
            addCriterion("parent_uuid >", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidGreaterThanOrEqualTo(String value) {
            addCriterion("parent_uuid >=", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidLessThan(String value) {
            addCriterion("parent_uuid <", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidLessThanOrEqualTo(String value) {
            addCriterion("parent_uuid <=", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidLike(String value) {
            addCriterion("parent_uuid like", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidNotLike(String value) {
            addCriterion("parent_uuid not like", value, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidIn(List<String> values) {
            addCriterion("parent_uuid in", values, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidNotIn(List<String> values) {
            addCriterion("parent_uuid not in", values, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidBetween(String value1, String value2) {
            addCriterion("parent_uuid between", value1, value2, "parentUuid");
            return (Criteria) this;
        }

        public Criteria andParentUuidNotBetween(String value1, String value2) {
            addCriterion("parent_uuid not between", value1, value2, "parentUuid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_sellerprofiles
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}