package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingStoreCategoryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public TradingStoreCategoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
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
     * This method corresponds to the database table trading_store_category
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
     * This method corresponds to the database table trading_store_category
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_store_category
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
     * This class corresponds to the database table trading_store_category
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

        public Criteria andEbayAccountIdIsNull() {
            addCriterion("ebay_account_id is null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdIsNotNull() {
            addCriterion("ebay_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdEqualTo(Long value) {
            addCriterion("ebay_account_id =", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdNotEqualTo(Long value) {
            addCriterion("ebay_account_id <>", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdGreaterThan(Long value) {
            addCriterion("ebay_account_id >", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ebay_account_id >=", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdLessThan(Long value) {
            addCriterion("ebay_account_id <", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdLessThanOrEqualTo(Long value) {
            addCriterion("ebay_account_id <=", value, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdIn(List<Long> values) {
            addCriterion("ebay_account_id in", values, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdNotIn(List<Long> values) {
            addCriterion("ebay_account_id not in", values, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdBetween(Long value1, Long value2) {
            addCriterion("ebay_account_id between", value1, value2, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIdNotBetween(Long value1, Long value2) {
            addCriterion("ebay_account_id not between", value1, value2, "ebayAccountId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("site_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("site_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(String value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(String value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(String value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(String value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(String value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLike(String value) {
            addCriterion("site_id like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotLike(String value) {
            addCriterion("site_id not like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<String> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<String> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(String value1, String value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(String value1, String value2) {
            addCriterion("site_id not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdIsNull() {
            addCriterion("store_Category_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdIsNotNull() {
            addCriterion("store_Category_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdEqualTo(String value) {
            addCriterion("store_Category_id =", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdNotEqualTo(String value) {
            addCriterion("store_Category_id <>", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdGreaterThan(String value) {
            addCriterion("store_Category_id >", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("store_Category_id >=", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdLessThan(String value) {
            addCriterion("store_Category_id <", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("store_Category_id <=", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdLike(String value) {
            addCriterion("store_Category_id like", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdNotLike(String value) {
            addCriterion("store_Category_id not like", value, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdIn(List<String> values) {
            addCriterion("store_Category_id in", values, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdNotIn(List<String> values) {
            addCriterion("store_Category_id not in", values, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdBetween(String value1, String value2) {
            addCriterion("store_Category_id between", value1, value2, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryIdNotBetween(String value1, String value2) {
            addCriterion("store_Category_id not between", value1, value2, "storeCategoryId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdIsNull() {
            addCriterion("store_Category_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdIsNotNull() {
            addCriterion("store_Category_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdEqualTo(String value) {
            addCriterion("store_Category_parent_id =", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdNotEqualTo(String value) {
            addCriterion("store_Category_parent_id <>", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdGreaterThan(String value) {
            addCriterion("store_Category_parent_id >", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("store_Category_parent_id >=", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdLessThan(String value) {
            addCriterion("store_Category_parent_id <", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdLessThanOrEqualTo(String value) {
            addCriterion("store_Category_parent_id <=", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdLike(String value) {
            addCriterion("store_Category_parent_id like", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdNotLike(String value) {
            addCriterion("store_Category_parent_id not like", value, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdIn(List<String> values) {
            addCriterion("store_Category_parent_id in", values, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdNotIn(List<String> values) {
            addCriterion("store_Category_parent_id not in", values, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdBetween(String value1, String value2) {
            addCriterion("store_Category_parent_id between", value1, value2, "storeCategoryParentId");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryParentIdNotBetween(String value1, String value2) {
            addCriterion("store_Category_parent_id not between", value1, value2, "storeCategoryParentId");
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

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameIsNull() {
            addCriterion("store_category_name is null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameIsNotNull() {
            addCriterion("store_category_name is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameEqualTo(String value) {
            addCriterion("store_category_name =", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameNotEqualTo(String value) {
            addCriterion("store_category_name <>", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameGreaterThan(String value) {
            addCriterion("store_category_name >", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("store_category_name >=", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameLessThan(String value) {
            addCriterion("store_category_name <", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("store_category_name <=", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameLike(String value) {
            addCriterion("store_category_name like", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameNotLike(String value) {
            addCriterion("store_category_name not like", value, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameIn(List<String> values) {
            addCriterion("store_category_name in", values, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameNotIn(List<String> values) {
            addCriterion("store_category_name not in", values, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameBetween(String value1, String value2) {
            addCriterion("store_category_name between", value1, value2, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreCategoryNameNotBetween(String value1, String value2) {
            addCriterion("store_category_name not between", value1, value2, "storeCategoryName");
            return (Criteria) this;
        }

        public Criteria andStoreOrderIsNull() {
            addCriterion("store_order is null");
            return (Criteria) this;
        }

        public Criteria andStoreOrderIsNotNull() {
            addCriterion("store_order is not null");
            return (Criteria) this;
        }

        public Criteria andStoreOrderEqualTo(Integer value) {
            addCriterion("store_order =", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderNotEqualTo(Integer value) {
            addCriterion("store_order <>", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderGreaterThan(Integer value) {
            addCriterion("store_order >", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_order >=", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderLessThan(Integer value) {
            addCriterion("store_order <", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderLessThanOrEqualTo(Integer value) {
            addCriterion("store_order <=", value, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderIn(List<Integer> values) {
            addCriterion("store_order in", values, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderNotIn(List<Integer> values) {
            addCriterion("store_order not in", values, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderBetween(Integer value1, Integer value2) {
            addCriterion("store_order between", value1, value2, "storeOrder");
            return (Criteria) this;
        }

        public Criteria andStoreOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("store_order not between", value1, value2, "storeOrder");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_store_category
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
     * This class corresponds to the database table trading_store_category
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