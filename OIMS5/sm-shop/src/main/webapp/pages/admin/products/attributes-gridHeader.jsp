<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>	



				{title:"<s:message code="label.entity.id" text="Id"/>", name:"attributeId", canFilter:false},
        		{title:"<s:message code="" text="Attribute name"/>", name:"attribute", canFilter:false},
        		{title:"<s:message code="" text="Attribute value"/>", name:"value", canFilter:false},
        		{title:"<s:message code="label.entity.order" text="Order"/>", name:"order", canFilter:false},
        		{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}