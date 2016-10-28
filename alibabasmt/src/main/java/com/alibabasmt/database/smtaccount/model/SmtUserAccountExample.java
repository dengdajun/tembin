package com.alibabasmt.database.smtaccount.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmtUserAccountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public SmtUserAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
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
     * This method corresponds to the database table smt_user_account
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
     * This method corresponds to the database table smt_user_account
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_user_account
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
     * This class corresponds to the database table smt_user_account
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

        public Criteria andSmtAccountNameIsNull() {
            addCriterion("smt_account_name is null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameIsNotNull() {
            addCriterion("smt_account_name is not null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameEqualTo(String value) {
            addCriterion("smt_account_name =", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameNotEqualTo(String value) {
            addCriterion("smt_account_name <>", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameGreaterThan(String value) {
            addCriterion("smt_account_name >", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("smt_account_name >=", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameLessThan(String value) {
            addCriterion("smt_account_name <", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameLessThanOrEqualTo(String value) {
            addCriterion("smt_account_name <=", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameLike(String value) {
            addCriterion("smt_account_name like", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameNotLike(String value) {
            addCriterion("smt_account_name not like", value, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameIn(List<String> values) {
            addCriterion("smt_account_name in", values, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameNotIn(List<String> values) {
            addCriterion("smt_account_name not in", values, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameBetween(String value1, String value2) {
            addCriterion("smt_account_name between", value1, value2, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNameNotBetween(String value1, String value2) {
            addCriterion("smt_account_name not between", value1, value2, "smtAccountName");
            return (Criteria) this;
        }

        public Criteria andSmtAccountIsNull() {
            addCriterion("smt_account is null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountIsNotNull() {
            addCriterion("smt_account is not null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountEqualTo(String value) {
            addCriterion("smt_account =", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNotEqualTo(String value) {
            addCriterion("smt_account <>", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountGreaterThan(String value) {
            addCriterion("smt_account >", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountGreaterThanOrEqualTo(String value) {
            addCriterion("smt_account >=", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountLessThan(String value) {
            addCriterion("smt_account <", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountLessThanOrEqualTo(String value) {
            addCriterion("smt_account <=", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountLike(String value) {
            addCriterion("smt_account like", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNotLike(String value) {
            addCriterion("smt_account not like", value, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountIn(List<String> values) {
            addCriterion("smt_account in", values, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNotIn(List<String> values) {
            addCriterion("smt_account not in", values, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountBetween(String value1, String value2) {
            addCriterion("smt_account between", value1, value2, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtAccountNotBetween(String value1, String value2) {
            addCriterion("smt_account not between", value1, value2, "smtAccount");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenIsNull() {
            addCriterion("smt_freshtoken is null");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenIsNotNull() {
            addCriterion("smt_freshtoken is not null");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenEqualTo(String value) {
            addCriterion("smt_freshtoken =", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenNotEqualTo(String value) {
            addCriterion("smt_freshtoken <>", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenGreaterThan(String value) {
            addCriterion("smt_freshtoken >", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenGreaterThanOrEqualTo(String value) {
            addCriterion("smt_freshtoken >=", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenLessThan(String value) {
            addCriterion("smt_freshtoken <", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenLessThanOrEqualTo(String value) {
            addCriterion("smt_freshtoken <=", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenLike(String value) {
            addCriterion("smt_freshtoken like", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenNotLike(String value) {
            addCriterion("smt_freshtoken not like", value, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenIn(List<String> values) {
            addCriterion("smt_freshtoken in", values, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenNotIn(List<String> values) {
            addCriterion("smt_freshtoken not in", values, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenBetween(String value1, String value2) {
            addCriterion("smt_freshtoken between", value1, value2, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andSmtFreshtokenNotBetween(String value1, String value2) {
            addCriterion("smt_freshtoken not between", value1, value2, "smtFreshtoken");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameIsNull() {
            addCriterion("smt_account_shortname is null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameIsNotNull() {
            addCriterion("smt_account_shortname is not null");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameEqualTo(String value) {
            addCriterion("smt_account_shortname =", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameNotEqualTo(String value) {
            addCriterion("smt_account_shortname <>", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameGreaterThan(String value) {
            addCriterion("smt_account_shortname >", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameGreaterThanOrEqualTo(String value) {
            addCriterion("smt_account_shortname >=", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameLessThan(String value) {
            addCriterion("smt_account_shortname <", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameLessThanOrEqualTo(String value) {
            addCriterion("smt_account_shortname <=", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameLike(String value) {
            addCriterion("smt_account_shortname like", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameNotLike(String value) {
            addCriterion("smt_account_shortname not like", value, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameIn(List<String> values) {
            addCriterion("smt_account_shortname in", values, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameNotIn(List<String> values) {
            addCriterion("smt_account_shortname not in", values, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameBetween(String value1, String value2) {
            addCriterion("smt_account_shortname between", value1, value2, "smtAccountShortname");
            return (Criteria) this;
        }

        public Criteria andSmtAccountShortnameNotBetween(String value1, String value2) {
            addCriterion("smt_account_shortname not between", value1, value2, "smtAccountShortname");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table smt_user_account
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
     * This class corresponds to the database table smt_user_account
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