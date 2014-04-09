<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.serial.number" text="SNO."/>", name:"serialNumber", canFilter:false},
{title:"<s:message code="label.entity.invoice.type" text="Invoice Series"/>", name:"invoiceType", canFilter:false},	
{title:"<s:message code="label.generic.invoice.date" text="Date"/>", name:"invoiceDate", canFilter:false},
{title:"<s:message code="label.generic.invoice.customer" text="Customer"/>", name:"customer", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}