package xdu.backend.pojo;

import java.util.ArrayList;
import java.util.List;

public class BookExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andBook_idIsNull() {
            addCriterion("book_id is null");
            return (Criteria) this;
        }

        public Criteria andBook_idIsNotNull() {
            addCriterion("book_id is not null");
            return (Criteria) this;
        }

        public Criteria andBook_idEqualTo(String value) {
            addCriterion("book_id =", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idNotEqualTo(String value) {
            addCriterion("book_id <>", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idGreaterThan(String value) {
            addCriterion("book_id >", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idGreaterThanOrEqualTo(String value) {
            addCriterion("book_id >=", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idLessThan(String value) {
            addCriterion("book_id <", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idLessThanOrEqualTo(String value) {
            addCriterion("book_id <=", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idLike(String value) {
            addCriterion("book_id like", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idNotLike(String value) {
            addCriterion("book_id not like", value, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idIn(List<String> values) {
            addCriterion("book_id in", values, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idNotIn(List<String> values) {
            addCriterion("book_id not in", values, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idBetween(String value1, String value2) {
            addCriterion("book_id between", value1, value2, "book_id");
            return (Criteria) this;
        }

        public Criteria andBook_idNotBetween(String value1, String value2) {
            addCriterion("book_id not between", value1, value2, "book_id");
            return (Criteria) this;
        }

        public Criteria andNnameIsNull() {
            addCriterion("nname is null");
            return (Criteria) this;
        }

        public Criteria andNnameIsNotNull() {
            addCriterion("nname is not null");
            return (Criteria) this;
        }

        public Criteria andNnameEqualTo(String value) {
            addCriterion("nname =", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameNotEqualTo(String value) {
            addCriterion("nname <>", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameGreaterThan(String value) {
            addCriterion("nname >", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameGreaterThanOrEqualTo(String value) {
            addCriterion("nname >=", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameLessThan(String value) {
            addCriterion("nname <", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameLessThanOrEqualTo(String value) {
            addCriterion("nname <=", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameLike(String value) {
            addCriterion("nname like", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameNotLike(String value) {
            addCriterion("nname not like", value, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameIn(List<String> values) {
            addCriterion("nname in", values, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameNotIn(List<String> values) {
            addCriterion("nname not in", values, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameBetween(String value1, String value2) {
            addCriterion("nname between", value1, value2, "nname");
            return (Criteria) this;
        }

        public Criteria andNnameNotBetween(String value1, String value2) {
            addCriterion("nname not between", value1, value2, "nname");
            return (Criteria) this;
        }

        public Criteria andFreeIsNull() {
            addCriterion("`free` is null");
            return (Criteria) this;
        }

        public Criteria andFreeIsNotNull() {
            addCriterion("`free` is not null");
            return (Criteria) this;
        }

        public Criteria andFreeEqualTo(Short value) {
            addCriterion("`free` =", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeNotEqualTo(Short value) {
            addCriterion("`free` <>", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeGreaterThan(Short value) {
            addCriterion("`free` >", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeGreaterThanOrEqualTo(Short value) {
            addCriterion("`free` >=", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeLessThan(Short value) {
            addCriterion("`free` <", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeLessThanOrEqualTo(Short value) {
            addCriterion("`free` <=", value, "free");
            return (Criteria) this;
        }

        public Criteria andFreeIn(List<Short> values) {
            addCriterion("`free` in", values, "free");
            return (Criteria) this;
        }

        public Criteria andFreeNotIn(List<Short> values) {
            addCriterion("`free` not in", values, "free");
            return (Criteria) this;
        }

        public Criteria andFreeBetween(Short value1, Short value2) {
            addCriterion("`free` between", value1, value2, "free");
            return (Criteria) this;
        }

        public Criteria andFreeNotBetween(Short value1, Short value2) {
            addCriterion("`free` not between", value1, value2, "free");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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