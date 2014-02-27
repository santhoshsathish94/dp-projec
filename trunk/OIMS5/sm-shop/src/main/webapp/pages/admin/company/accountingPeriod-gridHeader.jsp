<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.id" text="Id"/>", name:"accountingPeriodId", canFilter:false},
{title:"<s:message code="label.entity.accountingPeriod" text="Accounting Period"/>", name:"accPeriod", canFilter:false},	
{title:"<s:message code="label.generic.status" text="Status"/>", name:"status", canFilter:false},
{title:"<s:message code="label.entity.default" text="Default"/>", name:"default", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}