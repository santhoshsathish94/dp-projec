<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.currency.symbol" text="Currency Symbol"/>", name:"symbol", canFilter:false},	
{title:"<s:message code="label.generic.currency.code" text="Currency Code"/>", name:"code", canFilter:false},
{title:"<s:message code="label.entity.currency.decimalPlace" text="Decimal Places"/>", name:"decimalPlace", canFilter:false},
{title:"<s:message code="label.entity.currency.exchangeRate" text="Exchange Rate(in INR)"/>", name:"exchangeRate", canFilter:false},
{title:"<s:message code="label.entity.action" text="Action"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}