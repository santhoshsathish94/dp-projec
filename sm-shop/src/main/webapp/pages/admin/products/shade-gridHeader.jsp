<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	


{title:"<s:message code="label.entity.srn" text="Sr. No."/>", name:"counter", canFilter:false},
{title:"<s:message code="label.product.shade.name" text="Shade Name"/>", name:"shade", canFilter:false},	
{title:"<s:message code="label.product.shade.short.name" text="Shade Short Name"/>", name:"shadeShortName", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}