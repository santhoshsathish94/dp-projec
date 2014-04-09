<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.party.name" text="Number Series"/>", name:"numberSeries", canFilter:false},	
{title:"<s:message code="label.generic.item.name" text="Current Number"/>", name:"currentNumber", canFilter:false},
{title:"<s:message code="label.generic.company.name" text="Current Series"/>", name:"currentSeries", canFilter:false}
