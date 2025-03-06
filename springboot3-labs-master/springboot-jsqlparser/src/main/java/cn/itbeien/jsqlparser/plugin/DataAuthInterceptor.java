package cn.itbeien.jsqlparser.plugin;


import io.micrometer.common.util.StringUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Component
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
        }
)
public class DataAuthInterceptor implements Interceptor {
    /**
     * 动态条件
     * 该条件在实际的应用中通过参数动态传入，然后在代码中动态获取
     */
    private static final String createUser ="admin";
    /**
     * 动态条件
     * 该条件在实际的应用中通过参数动态传入，然后在代码中动态获取
     */
    private  static final List<Map<String,String>> columns  = new ArrayList<Map<String,String>>();
    static {
        Map<String,String> values = new HashMap<>();
        values.put("create_user","admin");
        values.put("name","itbeien");
        columns.add(values);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        //确保只有拦截的目标对象是 StatementHandler 类型时才执行特定逻辑
        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            // 获取 BoundSql 对象，包含原始 SQL 语句
            BoundSql boundSql = statementHandler.getBoundSql();
            String originalSql = boundSql.getSql();
            String newSql = setColumnToStatement(originalSql);
            // 使用MetaObject对象将新的SQL语句设置到BoundSql对象中
            MetaObject metaObject = SystemMetaObject.forObject(boundSql);
            metaObject.setValue("sql", newSql);
        }
        // 执行SQL
        return invocation.proceed();
    }

    private String setColumnToStatement(String originalSql) {
        net.sf.jsqlparser.statement.Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(originalSql);
        } catch (JSQLParserException e) {
            throw new RuntimeException("setColumnToStatement-SQL语句解析异常："+originalSql);
        }
        if (statement instanceof Select) {
            Select select = (Select) statement;
            PlainSelect selectBody = select.getSelectBody(PlainSelect.class);
            if (selectBody.getFromItem() instanceof Table) {
                Expression newWhereExpression;
                if (selectBody.getJoins() == null || selectBody.getJoins().isEmpty()) {
                    newWhereExpression = setColumnToWhereExpression(selectBody.getWhere(), null);
                } else {
                    // 如果是多表关联查询，在关联查询中新增每个表的数据权限字段条件
                    newWhereExpression = multipleTableJoinWhereExpression(selectBody);
                }
                // 将新的where设置到Select中
                selectBody.setWhere(newWhereExpression);
            } else if (selectBody.getFromItem() instanceof SubSelect) {
                // 如果是子查询，在子查询中新增数据权限字段条件
                // 当前方法只能处理单层子查询，如果有多层级的子查询的场景需要通过递归设置数据权限字段
                SubSelect subSelect = (SubSelect) selectBody.getFromItem();
                PlainSelect subSelectBody = subSelect.getSelectBody(PlainSelect.class);
                Expression newWhereExpression = setColumnToWhereExpression(subSelectBody.getWhere(), null);
                subSelectBody.setWhere(newWhereExpression);
            }

            // 获得修改后的语句
            return select.toString();
        } else if (statement instanceof Insert) {
            Insert insert = (Insert) statement;
            setColumnToInsert(insert);

            return insert.toString();
        } else if (statement instanceof Update) {
            Update update = (Update) statement;
            Expression newWhereExpression = setColumnToWhereExpression(update.getWhere(),null);
            // 将新的where设置到Update中
            update.setWhere(newWhereExpression);

            return update.toString();
        } else if (statement instanceof Delete) {
            Delete delete = (Delete) statement;
            Expression newWhereExpression = setColumnToWhereExpression(delete.getWhere(),null);
            // 将新的where设置到delete中
            delete.setWhere(newWhereExpression);

            return delete.toString();
        }
        return originalSql;
    }

    /**
     * 将需要实现数据权限控制的字段加入到SQL的Where语法树中
     * @param whereExpression SQL的Where语法树
     * @param alias 表别名
     * @return 新的SQL Where语法树
     */
    private Expression setColumnToWhereExpression(Expression whereExpression, String alias) {
        // 添加SQL语法树的一个where分支，并添加指定条件
        for(Map<String,String> column : columns){
            for(Map.Entry<String, String> entry : column.entrySet()){
                EqualsTo columnEquals = new EqualsTo();
                columnEquals.setLeftExpression(new Column(StringUtils.isNotBlank(alias) ? String.format("%s."+entry.getKey(), alias) : entry.getKey()));
                columnEquals.setRightExpression(new StringValue(entry.getValue()));
                if (whereExpression == null){
                    whereExpression =  columnEquals;
                } else {
                    AndExpression andExpression = new AndExpression();
                    // 将新的where条件加入到原where条件的右分支树
                    andExpression.setRightExpression(columnEquals);
                    andExpression.setLeftExpression(whereExpression);
                    whereExpression =  andExpression;
                }
            }

        }
        return whereExpression;
    }

    /**
     * 多表关联查询时，给关联的所有表加入数据权限控制条件
     * @param selectBody select语法树
     * @return 新的SQL Where语法树
     */
    private Expression multipleTableJoinWhereExpression(PlainSelect selectBody){
        Table mainTable = selectBody.getFromItem(Table.class);
        String mainTableAlias = mainTable.getAlias().getName();
        // 将 t1.create_user = createUser 的条件添加到where中
        Expression newWhereExpression = setColumnToWhereExpression(selectBody.getWhere(), mainTableAlias);
        List<Join> joins = selectBody.getJoins();
        for (Join join : joins) {
            FromItem joinRightItem = join.getRightItem();
            if (joinRightItem instanceof Table) {
                Table joinTable = (Table) joinRightItem;
                String joinTableAlias = joinTable.getAlias().getName();
                // 将每一个join的 tx.create_user = createUser 的条件添加到where中
                newWhereExpression = setColumnToWhereExpression(newWhereExpression, joinTableAlias);
            }
        }
        return newWhereExpression;
    }

    /**
     * 新增数据时，插入指定字段
     * @param insert Insert 语法树
     */
    private void setColumnToInsert(Insert insert) {
        // 添加create_user列
        List<Column> columns = insert.getColumns();
        columns.add(new Column("create_user"));
        // values中添加指定字段变量值
        List<SelectBody> selects = insert.getSelect().getSelectBody(SetOperationList.class).getSelects();
        for (SelectBody select : selects) {
            if (select instanceof ValuesStatement){
                ValuesStatement valuesStatement = (ValuesStatement) select;
                ExpressionList expressions = (ExpressionList) valuesStatement.getExpressions();
                List<Expression> values = expressions.getExpressions();
                for (Expression expression : values){
                    if (expression instanceof RowConstructor) {
                        RowConstructor rowConstructor = (RowConstructor) expression;
                        ExpressionList exprList = rowConstructor.getExprList();
                        exprList.addExpressions(new StringValue(createUser));
                    }
                }
            }
        }
    }
}
