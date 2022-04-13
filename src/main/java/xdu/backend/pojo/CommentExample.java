package xdu.backend.pojo;

import java.util.ArrayList;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
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

        public Criteria andCcommentIsNull() {
            addCriterion("ccomment is null");
            return (Criteria) this;
        }

        public Criteria andCcommentIsNotNull() {
            addCriterion("ccomment is not null");
            return (Criteria) this;
        }

        public Criteria andCcommentEqualTo(String value) {
            addCriterion("ccomment =", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentNotEqualTo(String value) {
            addCriterion("ccomment <>", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentGreaterThan(String value) {
            addCriterion("ccomment >", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentGreaterThanOrEqualTo(String value) {
            addCriterion("ccomment >=", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentLessThan(String value) {
            addCriterion("ccomment <", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentLessThanOrEqualTo(String value) {
            addCriterion("ccomment <=", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentLike(String value) {
            addCriterion("ccomment like", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentNotLike(String value) {
            addCriterion("ccomment not like", value, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentIn(List<String> values) {
            addCriterion("ccomment in", values, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentNotIn(List<String> values) {
            addCriterion("ccomment not in", values, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentBetween(String value1, String value2) {
            addCriterion("ccomment between", value1, value2, "ccomment");
            return (Criteria) this;
        }

        public Criteria andCcommentNotBetween(String value1, String value2) {
            addCriterion("ccomment not between", value1, value2, "ccomment");
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