package com.alibabasmt.database.smtshipping.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmtShippingTemplateDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public SmtShippingTemplateDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
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
     * This method corresponds to the database table smt_shipping_template_detail
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
     * This method corresponds to the database table smt_shipping_template_detail
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_template_detail
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
     * This class corresponds to the database table smt_shipping_template_detail
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

        public Criteria andLogisticscompanyIsNull() {
            addCriterion("logisticsCompany is null");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyIsNotNull() {
            addCriterion("logisticsCompany is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyEqualTo(String value) {
            addCriterion("logisticsCompany =", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyNotEqualTo(String value) {
            addCriterion("logisticsCompany <>", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyGreaterThan(String value) {
            addCriterion("logisticsCompany >", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyGreaterThanOrEqualTo(String value) {
            addCriterion("logisticsCompany >=", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyLessThan(String value) {
            addCriterion("logisticsCompany <", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyLessThanOrEqualTo(String value) {
            addCriterion("logisticsCompany <=", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyLike(String value) {
            addCriterion("logisticsCompany like", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyNotLike(String value) {
            addCriterion("logisticsCompany not like", value, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyIn(List<String> values) {
            addCriterion("logisticsCompany in", values, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyNotIn(List<String> values) {
            addCriterion("logisticsCompany not in", values, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyBetween(String value1, String value2) {
            addCriterion("logisticsCompany between", value1, value2, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andLogisticscompanyNotBetween(String value1, String value2) {
            addCriterion("logisticsCompany not between", value1, value2, "logisticscompany");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingIsNull() {
            addCriterion("allStandardShipping is null");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingIsNotNull() {
            addCriterion("allStandardShipping is not null");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingEqualTo(String value) {
            addCriterion("allStandardShipping =", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingNotEqualTo(String value) {
            addCriterion("allStandardShipping <>", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingGreaterThan(String value) {
            addCriterion("allStandardShipping >", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingGreaterThanOrEqualTo(String value) {
            addCriterion("allStandardShipping >=", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingLessThan(String value) {
            addCriterion("allStandardShipping <", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingLessThanOrEqualTo(String value) {
            addCriterion("allStandardShipping <=", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingLike(String value) {
            addCriterion("allStandardShipping like", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingNotLike(String value) {
            addCriterion("allStandardShipping not like", value, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingIn(List<String> values) {
            addCriterion("allStandardShipping in", values, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingNotIn(List<String> values) {
            addCriterion("allStandardShipping not in", values, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingBetween(String value1, String value2) {
            addCriterion("allStandardShipping between", value1, value2, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandardshippingNotBetween(String value1, String value2) {
            addCriterion("allStandardShipping not between", value1, value2, "allstandardshipping");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountIsNull() {
            addCriterion("allStandardDiscount is null");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountIsNotNull() {
            addCriterion("allStandardDiscount is not null");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountEqualTo(Double value) {
            addCriterion("allStandardDiscount =", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountNotEqualTo(Double value) {
            addCriterion("allStandardDiscount <>", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountGreaterThan(Double value) {
            addCriterion("allStandardDiscount >", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountGreaterThanOrEqualTo(Double value) {
            addCriterion("allStandardDiscount >=", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountLessThan(Double value) {
            addCriterion("allStandardDiscount <", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountLessThanOrEqualTo(Double value) {
            addCriterion("allStandardDiscount <=", value, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountIn(List<Double> values) {
            addCriterion("allStandardDiscount in", values, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountNotIn(List<Double> values) {
            addCriterion("allStandardDiscount not in", values, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountBetween(Double value1, Double value2) {
            addCriterion("allStandardDiscount between", value1, value2, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllstandarddiscountNotBetween(Double value1, Double value2) {
            addCriterion("allStandardDiscount not between", value1, value2, "allstandarddiscount");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingIsNull() {
            addCriterion("allFreeShipping is null");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingIsNotNull() {
            addCriterion("allFreeShipping is not null");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingEqualTo(String value) {
            addCriterion("allFreeShipping =", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingNotEqualTo(String value) {
            addCriterion("allFreeShipping <>", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingGreaterThan(String value) {
            addCriterion("allFreeShipping >", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingGreaterThanOrEqualTo(String value) {
            addCriterion("allFreeShipping >=", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingLessThan(String value) {
            addCriterion("allFreeShipping <", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingLessThanOrEqualTo(String value) {
            addCriterion("allFreeShipping <=", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingLike(String value) {
            addCriterion("allFreeShipping like", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingNotLike(String value) {
            addCriterion("allFreeShipping not like", value, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingIn(List<String> values) {
            addCriterion("allFreeShipping in", values, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingNotIn(List<String> values) {
            addCriterion("allFreeShipping not in", values, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingBetween(String value1, String value2) {
            addCriterion("allFreeShipping between", value1, value2, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andAllfreeshippingNotBetween(String value1, String value2) {
            addCriterion("allFreeShipping not between", value1, value2, "allfreeshipping");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountIsNull() {
            addCriterion("standardShippingDiscount is null");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountIsNotNull() {
            addCriterion("standardShippingDiscount is not null");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountEqualTo(Double value) {
            addCriterion("standardShippingDiscount =", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountNotEqualTo(Double value) {
            addCriterion("standardShippingDiscount <>", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountGreaterThan(Double value) {
            addCriterion("standardShippingDiscount >", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountGreaterThanOrEqualTo(Double value) {
            addCriterion("standardShippingDiscount >=", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountLessThan(Double value) {
            addCriterion("standardShippingDiscount <", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountLessThanOrEqualTo(Double value) {
            addCriterion("standardShippingDiscount <=", value, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountIn(List<Double> values) {
            addCriterion("standardShippingDiscount in", values, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountNotIn(List<Double> values) {
            addCriterion("standardShippingDiscount not in", values, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountBetween(Double value1, Double value2) {
            addCriterion("standardShippingDiscount between", value1, value2, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andStandardshippingdiscountNotBetween(Double value1, Double value2) {
            addCriterion("standardShippingDiscount not between", value1, value2, "standardshippingdiscount");
            return (Criteria) this;
        }

        public Criteria andIsSelfIsNull() {
            addCriterion("is_self is null");
            return (Criteria) this;
        }

        public Criteria andIsSelfIsNotNull() {
            addCriterion("is_self is not null");
            return (Criteria) this;
        }

        public Criteria andIsSelfEqualTo(String value) {
            addCriterion("is_self =", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfNotEqualTo(String value) {
            addCriterion("is_self <>", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfGreaterThan(String value) {
            addCriterion("is_self >", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfGreaterThanOrEqualTo(String value) {
            addCriterion("is_self >=", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfLessThan(String value) {
            addCriterion("is_self <", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfLessThanOrEqualTo(String value) {
            addCriterion("is_self <=", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfLike(String value) {
            addCriterion("is_self like", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfNotLike(String value) {
            addCriterion("is_self not like", value, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfIn(List<String> values) {
            addCriterion("is_self in", values, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfNotIn(List<String> values) {
            addCriterion("is_self not in", values, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfBetween(String value1, String value2) {
            addCriterion("is_self between", value1, value2, "isSelf");
            return (Criteria) this;
        }

        public Criteria andIsSelfNotBetween(String value1, String value2) {
            addCriterion("is_self not between", value1, value2, "isSelf");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table smt_shipping_template_detail
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
     * This class corresponds to the database table smt_shipping_template_detail
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