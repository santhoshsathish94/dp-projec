<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="" text="Id"/>", name:"Id", canFilter:false},
{title:"<s:message code="" text="Ref No"/>", name:"Ref-No", canFilter:false},
{title:"<s:message code="" text="Date"/>", name:"Date", canFilter:false},
{title:"<s:message code="" text="Debit"/>", name:"Debit", canFilter:false},
{title:"<s:message code="" text="Debit Amount"/>", name:"Debit-Amount", canFilter:false},
{title:"<s:message code="" text="Credit"/>", name:"Credit", canFilter:false},
{title:"<s:message code="" text="Credit Amount"/>", name:"Credit-Amount", canFilter:false},
{title:"<s:message code="" text="Narration"/>", name:"Narration", canFilter:false}