package com.base.database.task.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskComplementExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public TaskComplementExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
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
     * This method corresponds to the database table task_complement
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
     * This method corresponds to the database table task_complement
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_complement
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
     * This class corresponds to the database table task_complement
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

        public Criteria andRepValueIsNull() {
            addCriterion("rep_value is null");
            return (Criteria) this;
        }

        public Criteria andRepValueIsNotNull() {
            addCriterion("rep_value is not null");
            return (Criteria) this;
        }

        public Criteria andRepValueEqualTo(String value) {
            addCriterion("rep_value =", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueNotEqualTo(String value) {
            addCriterion("rep_value <>", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueGreaterThan(String value) {
            addCriterion("rep_value >", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueGreaterThanOrEqualTo(String value) {
            addCriterion("rep_value >=", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueLessThan(String value) {
            addCriterion("rep_value <", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueLessThanOrEqualTo(String value) {
            addCriterion("rep_value <=", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueLike(String value) {
            addCriterion("rep_value like", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueNotLike(String value) {
            addCriterion("rep_value not like", value, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueIn(List<String> values) {
            addCriterion("rep_value in", values, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueNotIn(List<String> values) {
            addCriterion("rep_value not in", values, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueBetween(String value1, String value2) {
            addCriterion("rep_value between", value1, value2, "repValue");
            return (Criteria) this;
        }

        public Criteria andRepValueNotBetween(String value1, String value2) {
            addCriterion("rep_value not between", value1, value2, "repValue");
            return (Criteria) this;
        }

        public Criteria andOldValueIsNull() {
            addCriterion("old_value is null");
            return (Criteria) this;
        }

        public Criteria andOldValueIsNotNull() {
            addCriterion("old_value is not null");
            return (Criteria) this;
        }

        public Criteria andOldValueEqualTo(String value) {
            addCriterion("old_value =", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueNotEqualTo(String value) {
            addCriterion("old_value <>", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueGreaterThan(String value) {
            addCriterion("old_value >", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueGreaterThanOrEqualTo(String value) {
            addCriterion("old_value >=", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueLessThan(String value) {
            addCriterion("old_value <", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueLessThanOrEqualTo(String value) {
            addCriterion("old_value <=", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueLike(String value) {
            addCriterion("old_value like", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueNotLike(String value) {
            addCriterion("old_value not like", value, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueIn(List<String> values) {
            addCriterion("old_value in", values, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueNotIn(List<String> values) {
            addCriterion("old_value not in", values, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueBetween(String value1, String value2) {
            addCriterion("old_value between", value1, value2, "oldValue");
            return (Criteria) this;
        }

        public Criteria andOldValueNotBetween(String value1, String value2) {
            addCriterion("old_value not between", value1, value2, "oldValue");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIsNull() {
            addCriterion("ebay_account is null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIsNotNull() {
            addCriterion("ebay_account is not null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountEqualTo(String value) {
            addCriterion("ebay_account =", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotEqualTo(String value) {
            addCriterion("ebay_account <>", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountGreaterThan(String value) {
            addCriterion("ebay_account >", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountGreaterThanOrEqualTo(String value) {
            addCriterion("ebay_account >=", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLessThan(String value) {
            addCriterion("ebay_account <", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLessThanOrEqualTo(String value) {
            addCriterion("ebay_account <=", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLike(String value) {
            addCriterion("ebay_account like", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotLike(String value) {
            addCriterion("ebay_account not like", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIn(List<String> values) {
            addCriterion("ebay_account in", values, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotIn(List<String> values) {
            addCriterion("ebay_account not in", values, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountBetween(String value1, String value2) {
            addCriterion("ebay_account between", value1, value2, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotBetween(String value1, String value2) {
            addCriterion("ebay_account not between", value1, value2, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andSiteIsNull() {
            addCriterion("site is null");
            return (Criteria) this;
        }

        public Criteria andSiteIsNotNull() {
            addCriterion("site is not null");
            return (Criteria) this;
        }

        public Criteria andSiteEqualTo(String value) {
            addCriterion("site =", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotEqualTo(String value) {
            addCriterion("site <>", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteGreaterThan(String value) {
            addCriterion("site >", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteGreaterThanOrEqualTo(String value) {
            addCriterion("site >=", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLessThan(String value) {
            addCriterion("site <", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLessThanOrEqualTo(String value) {
            addCriterion("site <=", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLike(String value) {
            addCriterion("site like", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotLike(String value) {
            addCriterion("site not like", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteIn(List<String> values) {
            addCriterion("site in", values, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotIn(List<String> values) {
            addCriterion("site not in", values, "site");
            return (Criteria) this;
        }

        public Criteria andSiteBetween(String value1, String value2) {
            addCriterion("site between", value1, value2, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotBetween(String value1, String value2) {
            addCriterion("site not between", value1, value2, "site");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(String value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(String value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(String value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(String value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(String value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(String value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLike(String value) {
            addCriterion("item_id like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotLike(String value) {
            addCriterion("item_id not like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<String> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<String> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(String value1, String value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(String value1, String value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andTaskFlagIsNull() {
            addCriterion("task_flag is null");
            return (Criteria) this;
        }

        public Criteria andTaskFlagIsNotNull() {
            addCriterion("task_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTaskFlagEqualTo(String value) {
            addCriterion("task_flag =", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagNotEqualTo(String value) {
            addCriterion("task_flag <>", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagGreaterThan(String value) {
            addCriterion("task_flag >", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagGreaterThanOrEqualTo(String value) {
            addCriterion("task_flag >=", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagLessThan(String value) {
            addCriterion("task_flag <", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagLessThanOrEqualTo(String value) {
            addCriterion("task_flag <=", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagLike(String value) {
            addCriterion("task_flag like", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagNotLike(String value) {
            addCriterion("task_flag not like", value, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagIn(List<String> values) {
            addCriterion("task_flag in", values, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagNotIn(List<String> values) {
            addCriterion("task_flag not in", values, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagBetween(String value1, String value2) {
            addCriterion("task_flag between", value1, value2, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andTaskFlagNotBetween(String value1, String value2) {
            addCriterion("task_flag not between", value1, value2, "taskFlag");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("data_type like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("data_type not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andRepPriceIsNull() {
            addCriterion("rep_price is null");
            return (Criteria) this;
        }

        public Criteria andRepPriceIsNotNull() {
            addCriterion("rep_price is not null");
            return (Criteria) this;
        }

        public Criteria andRepPriceEqualTo(Double value) {
            addCriterion("rep_price =", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceNotEqualTo(Double value) {
            addCriterion("rep_price <>", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceGreaterThan(Double value) {
            addCriterion("rep_price >", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("rep_price >=", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceLessThan(Double value) {
            addCriterion("rep_price <", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceLessThanOrEqualTo(Double value) {
            addCriterion("rep_price <=", value, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceIn(List<Double> values) {
            addCriterion("rep_price in", values, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceNotIn(List<Double> values) {
            addCriterion("rep_price not in", values, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceBetween(Double value1, Double value2) {
            addCriterion("rep_price between", value1, value2, "repPrice");
            return (Criteria) this;
        }

        public Criteria andRepPriceNotBetween(Double value1, Double value2) {
            addCriterion("rep_price not between", value1, value2, "repPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceIsNull() {
            addCriterion("old_price is null");
            return (Criteria) this;
        }

        public Criteria andOldPriceIsNotNull() {
            addCriterion("old_price is not null");
            return (Criteria) this;
        }

        public Criteria andOldPriceEqualTo(Double value) {
            addCriterion("old_price =", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceNotEqualTo(Double value) {
            addCriterion("old_price <>", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceGreaterThan(Double value) {
            addCriterion("old_price >", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("old_price >=", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceLessThan(Double value) {
            addCriterion("old_price <", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceLessThanOrEqualTo(Double value) {
            addCriterion("old_price <=", value, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceIn(List<Double> values) {
            addCriterion("old_price in", values, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceNotIn(List<Double> values) {
            addCriterion("old_price not in", values, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceBetween(Double value1, Double value2) {
            addCriterion("old_price between", value1, value2, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andOldPriceNotBetween(Double value1, Double value2) {
            addCriterion("old_price not between", value1, value2, "oldPrice");
            return (Criteria) this;
        }

        public Criteria andSkuIsNull() {
            addCriterion("sku is null");
            return (Criteria) this;
        }

        public Criteria andSkuIsNotNull() {
            addCriterion("sku is not null");
            return (Criteria) this;
        }

        public Criteria andSkuEqualTo(String value) {
            addCriterion("sku =", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotEqualTo(String value) {
            addCriterion("sku <>", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThan(String value) {
            addCriterion("sku >", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThanOrEqualTo(String value) {
            addCriterion("sku >=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThan(String value) {
            addCriterion("sku <", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThanOrEqualTo(String value) {
            addCriterion("sku <=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLike(String value) {
            addCriterion("sku like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotLike(String value) {
            addCriterion("sku not like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuIn(List<String> values) {
            addCriterion("sku in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotIn(List<String> values) {
            addCriterion("sku not in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuBetween(String value1, String value2) {
            addCriterion("sku between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotBetween(String value1, String value2) {
            addCriterion("sku not between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSoureIdIsNull() {
            addCriterion("soure_id is null");
            return (Criteria) this;
        }

        public Criteria andSoureIdIsNotNull() {
            addCriterion("soure_id is not null");
            return (Criteria) this;
        }

        public Criteria andSoureIdEqualTo(Long value) {
            addCriterion("soure_id =", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdNotEqualTo(Long value) {
            addCriterion("soure_id <>", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdGreaterThan(Long value) {
            addCriterion("soure_id >", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdGreaterThanOrEqualTo(Long value) {
            addCriterion("soure_id >=", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdLessThan(Long value) {
            addCriterion("soure_id <", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdLessThanOrEqualTo(Long value) {
            addCriterion("soure_id <=", value, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdIn(List<Long> values) {
            addCriterion("soure_id in", values, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdNotIn(List<Long> values) {
            addCriterion("soure_id not in", values, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdBetween(Long value1, Long value2) {
            addCriterion("soure_id between", value1, value2, "soureId");
            return (Criteria) this;
        }

        public Criteria andSoureIdNotBetween(Long value1, Long value2) {
            addCriterion("soure_id not between", value1, value2, "soureId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_complement
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
     * This class corresponds to the database table task_complement
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