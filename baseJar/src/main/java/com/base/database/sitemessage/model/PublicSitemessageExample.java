package com.base.database.sitemessage.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicSitemessageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public PublicSitemessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
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
     * This method corresponds to the database table public_sitemessage
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
     * This method corresponds to the database table public_sitemessage
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_sitemessage
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
     * This class corresponds to the database table public_sitemessage
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

        public List<Criterion> getAllCriteria() {
            return criteria;
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

        public Criteria andMessageToIsNull() {
            addCriterion("message_to is null");
            return (Criteria) this;
        }

        public Criteria andMessageToIsNotNull() {
            addCriterion("message_to is not null");
            return (Criteria) this;
        }

        public Criteria andMessageToEqualTo(Long value) {
            addCriterion("message_to =", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToNotEqualTo(Long value) {
            addCriterion("message_to <>", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToGreaterThan(Long value) {
            addCriterion("message_to >", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToGreaterThanOrEqualTo(Long value) {
            addCriterion("message_to >=", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToLessThan(Long value) {
            addCriterion("message_to <", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToLessThanOrEqualTo(Long value) {
            addCriterion("message_to <=", value, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToIn(List<Long> values) {
            addCriterion("message_to in", values, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToNotIn(List<Long> values) {
            addCriterion("message_to not in", values, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToBetween(Long value1, Long value2) {
            addCriterion("message_to between", value1, value2, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageToNotBetween(Long value1, Long value2) {
            addCriterion("message_to not between", value1, value2, "messageTo");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNull() {
            addCriterion("message_type is null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNotNull() {
            addCriterion("message_type is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeEqualTo(String value) {
            addCriterion("message_type =", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotEqualTo(String value) {
            addCriterion("message_type <>", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThan(String value) {
            addCriterion("message_type >", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("message_type >=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThan(String value) {
            addCriterion("message_type <", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThanOrEqualTo(String value) {
            addCriterion("message_type <=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLike(String value) {
            addCriterion("message_type like", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotLike(String value) {
            addCriterion("message_type not like", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIn(List<String> values) {
            addCriterion("message_type in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotIn(List<String> values) {
            addCriterion("message_type not in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeBetween(String value1, String value2) {
            addCriterion("message_type between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotBetween(String value1, String value2) {
            addCriterion("message_type not between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTitleIsNull() {
            addCriterion("message_title is null");
            return (Criteria) this;
        }

        public Criteria andMessageTitleIsNotNull() {
            addCriterion("message_title is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTitleEqualTo(String value) {
            addCriterion("message_title =", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleNotEqualTo(String value) {
            addCriterion("message_title <>", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleGreaterThan(String value) {
            addCriterion("message_title >", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleGreaterThanOrEqualTo(String value) {
            addCriterion("message_title >=", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleLessThan(String value) {
            addCriterion("message_title <", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleLessThanOrEqualTo(String value) {
            addCriterion("message_title <=", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleLike(String value) {
            addCriterion("message_title like", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleNotLike(String value) {
            addCriterion("message_title not like", value, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleIn(List<String> values) {
            addCriterion("message_title in", values, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleNotIn(List<String> values) {
            addCriterion("message_title not in", values, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleBetween(String value1, String value2) {
            addCriterion("message_title between", value1, value2, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageTitleNotBetween(String value1, String value2) {
            addCriterion("message_title not between", value1, value2, "messageTitle");
            return (Criteria) this;
        }

        public Criteria andMessageFromIsNull() {
            addCriterion("message_from is null");
            return (Criteria) this;
        }

        public Criteria andMessageFromIsNotNull() {
            addCriterion("message_from is not null");
            return (Criteria) this;
        }

        public Criteria andMessageFromEqualTo(String value) {
            addCriterion("message_from =", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromNotEqualTo(String value) {
            addCriterion("message_from <>", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromGreaterThan(String value) {
            addCriterion("message_from >", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromGreaterThanOrEqualTo(String value) {
            addCriterion("message_from >=", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromLessThan(String value) {
            addCriterion("message_from <", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromLessThanOrEqualTo(String value) {
            addCriterion("message_from <=", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromLike(String value) {
            addCriterion("message_from like", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromNotLike(String value) {
            addCriterion("message_from not like", value, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromIn(List<String> values) {
            addCriterion("message_from in", values, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromNotIn(List<String> values) {
            addCriterion("message_from not in", values, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromBetween(String value1, String value2) {
            addCriterion("message_from between", value1, value2, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andMessageFromNotBetween(String value1, String value2) {
            addCriterion("message_from not between", value1, value2, "messageFrom");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerIsNull() {
            addCriterion("orderAndSeller is null");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerIsNotNull() {
            addCriterion("orderAndSeller is not null");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerEqualTo(String value) {
            addCriterion("orderAndSeller =", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerNotEqualTo(String value) {
            addCriterion("orderAndSeller <>", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerGreaterThan(String value) {
            addCriterion("orderAndSeller >", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerGreaterThanOrEqualTo(String value) {
            addCriterion("orderAndSeller >=", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerLessThan(String value) {
            addCriterion("orderAndSeller <", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerLessThanOrEqualTo(String value) {
            addCriterion("orderAndSeller <=", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerLike(String value) {
            addCriterion("orderAndSeller like", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerNotLike(String value) {
            addCriterion("orderAndSeller not like", value, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerIn(List<String> values) {
            addCriterion("orderAndSeller in", values, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerNotIn(List<String> values) {
            addCriterion("orderAndSeller not in", values, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerBetween(String value1, String value2) {
            addCriterion("orderAndSeller between", value1, value2, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andOrderandsellerNotBetween(String value1, String value2) {
            addCriterion("orderAndSeller not between", value1, value2, "orderandseller");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNull() {
            addCriterion("message_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNotNull() {
            addCriterion("message_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageIdEqualTo(String value) {
            addCriterion("message_id =", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotEqualTo(String value) {
            addCriterion("message_id <>", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThan(String value) {
            addCriterion("message_id >", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThanOrEqualTo(String value) {
            addCriterion("message_id >=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThan(String value) {
            addCriterion("message_id <", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThanOrEqualTo(String value) {
            addCriterion("message_id <=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLike(String value) {
            addCriterion("message_id like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotLike(String value) {
            addCriterion("message_id not like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdIn(List<String> values) {
            addCriterion("message_id in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotIn(List<String> values) {
            addCriterion("message_id not in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdBetween(String value1, String value2) {
            addCriterion("message_id between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotBetween(String value1, String value2) {
            addCriterion("message_id not between", value1, value2, "messageId");
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

        public Criteria andReadedIsNull() {
            addCriterion("readed is null");
            return (Criteria) this;
        }

        public Criteria andReadedIsNotNull() {
            addCriterion("readed is not null");
            return (Criteria) this;
        }

        public Criteria andReadedEqualTo(String value) {
            addCriterion("readed =", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedNotEqualTo(String value) {
            addCriterion("readed <>", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedGreaterThan(String value) {
            addCriterion("readed >", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedGreaterThanOrEqualTo(String value) {
            addCriterion("readed >=", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedLessThan(String value) {
            addCriterion("readed <", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedLessThanOrEqualTo(String value) {
            addCriterion("readed <=", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedLike(String value) {
            addCriterion("readed like", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedNotLike(String value) {
            addCriterion("readed not like", value, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedIn(List<String> values) {
            addCriterion("readed in", values, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedNotIn(List<String> values) {
            addCriterion("readed not in", values, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedBetween(String value1, String value2) {
            addCriterion("readed between", value1, value2, "readed");
            return (Criteria) this;
        }

        public Criteria andReadedNotBetween(String value1, String value2) {
            addCriterion("readed not between", value1, value2, "readed");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public_sitemessage
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
     * This class corresponds to the database table public_sitemessage
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

        private String typeHandler;

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

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}