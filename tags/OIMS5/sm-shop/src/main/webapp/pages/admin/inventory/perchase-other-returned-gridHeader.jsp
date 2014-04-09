<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="" text="Id"/>", name:"Id", canFilter:false},
{title:"<s:message code="" text="Debit To"/>", name:"Debit_supplier", canFilter:false},
{title:"<s:message code="" text="Date"/>", name:"SDate", canFilter:false},
{title:"<s:message code="" text="Ref No"/>", name:"Debit_ref_number", canFilter:false},
{title:"<s:message code="" text="Amount"/>", name:"Amount", canFilter:false},
{title:"<s:message code="" text="Tax"/>", name:"Debit_tax", canFilter:false},
{title:"<s:message code="" text="Total Amount"/>", name:"Debit_total_amount", canFilter:false},
{title:"<s:message code="" text="Credit To"/>", name:"Debit_credit_to", canFilter:false}