<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	


{title:"<s:message code="label.entity.credit.note.type" text="Credit Note Type"/>", name:"creditNoteType", canFilter:false, showIf:"false"},
{title:"<s:message code="label.entity.credit.note.credit.customer" text="Credit To"/>", name:"creditCustomer", canFilter:false},	
{title:"<s:message code="label.generic.credit.note.debit.customer" text="Debit To"/>", name:"debitCustomer", canFilter:false},
{title:"<s:message code="label.generic.credit.note.date" text="Credit Note Date"/>", name:"noteDate", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}