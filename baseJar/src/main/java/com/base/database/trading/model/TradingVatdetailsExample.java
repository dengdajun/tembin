package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingVatdetailsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public TradingVatdetailsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
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
     * This method corresponds to the database table trading_vatdetails
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
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
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
     * This class corresponds to the database table trading_vatdetails
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

        public Criteria andBusinesssellerIsNull() {
            addCriterion("BusinessSeller is null");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerIsNotNull() {
            addCriterion("BusinessSeller is not null");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerEqualTo(String value) {
            addCriterion("BusinessSeller =", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerNotEqualTo(String value) {
            addCriterion("BusinessSeller <>", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerGreaterThan(String value) {
            addCriterion("BusinessSeller >", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerGreaterThanOrEqualTo(String value) {
            addCriterion("BusinessSeller >=", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerLessThan(String value) {
            addCriterion("BusinessSeller <", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerLessThanOrEqualTo(String value) {
            addCriterion("BusinessSeller <=", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerLike(String value) {
            addCriterion("BusinessSeller like", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerNotLike(String value) {
            addCriterion("BusinessSeller not like", value, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerIn(List<String> values) {
            addCriterion("BusinessSeller in", values, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerNotIn(List<String> values) {
            addCriterion("BusinessSeller not in", values, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerBetween(String value1, String value2) {
            addCriterion("BusinessSeller between", value1, value2, "businessseller");
            return (Criteria) this;
        }

        public Criteria andBusinesssellerNotBetween(String value1, String value2) {
            addCriterion("BusinessSeller not between", value1, value2, "businessseller");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessIsNull() {
            addCriterion("RestrictedToBusiness is null");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessIsNotNull() {
            addCriterion("RestrictedToBusiness is not null");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessEqualTo(String value) {
            addCriterion("RestrictedToBusiness =", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessNotEqualTo(String value) {
            addCriterion("RestrictedToBusiness <>", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessGreaterThan(String value) {
            addCriterion("RestrictedToBusiness >", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessGreaterThanOrEqualTo(String value) {
            addCriterion("RestrictedToBusiness >=", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessLessThan(String value) {
            addCriterion("RestrictedToBusiness <", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessLessThanOrEqualTo(String value) {
            addCriterion("RestrictedToBusiness <=", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessLike(String value) {
            addCriterion("RestrictedToBusiness like", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessNotLike(String value) {
            addCriterion("RestrictedToBusiness not like", value, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessIn(List<String> values) {
            addCriterion("RestrictedToBusiness in", values, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessNotIn(List<String> values) {
            addCriterion("RestrictedToBusiness not in", values, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessBetween(String value1, String value2) {
            addCriterion("RestrictedToBusiness between", value1, value2, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andRestrictedtobusinessNotBetween(String value1, String value2) {
            addCriterion("RestrictedToBusiness not between", value1, value2, "restrictedtobusiness");
            return (Criteria) this;
        }

        public Criteria andVatpercentIsNull() {
            addCriterion("VATPercent is null");
            return (Criteria) this;
        }

        public Criteria andVatpercentIsNotNull() {
            addCriterion("VATPercent is not null");
            return (Criteria) this;
        }

        public Criteria andVatpercentEqualTo(Double value) {
            addCriterion("VATPercent =", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentNotEqualTo(Double value) {
            addCriterion("VATPercent <>", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentGreaterThan(Double value) {
            addCriterion("VATPercent >", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentGreaterThanOrEqualTo(Double value) {
            addCriterion("VATPercent >=", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentLessThan(Double value) {
            addCriterion("VATPercent <", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentLessThanOrEqualTo(Double value) {
            addCriterion("VATPercent <=", value, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentIn(List<Double> values) {
            addCriterion("VATPercent in", values, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentNotIn(List<Double> values) {
            addCriterion("VATPercent not in", values, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentBetween(Double value1, Double value2) {
            addCriterion("VATPercent between", value1, value2, "vatpercent");
            return (Criteria) this;
        }

        public Criteria andVatpercentNotBetween(Double value1, Double value2) {
            addCriterion("VATPercent not between", value1, value2, "vatpercent");
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
     * This class corresponds to the database table trading_vatdetails
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
     * This class corresponds to the database table trading_vatdetails
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