<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.id" text="Id"/>", name:"Id", canFilter:false},
{title:"<s:message code="" text="From"/>", name:"store_from", canFilter:false},
{title:"<s:message code="" text="To"/>", name:"store_to", canFilter:false},
{title:"<s:message code="" text="Date"/>", name:"SDate", canFilter:false},
{title:"<s:message code="" text="Comment"/>", name:"comment", canFilter:false},
{title:"<s:message code="" text="Product"/>", name:"sku", canFilter:false},
{title:"<s:message code="" text="Availability"/>", name:"availability", canFilter:false},
{title:"<s:message code="" text="Quantity"/>", name:"quantity", canFilter:false}
