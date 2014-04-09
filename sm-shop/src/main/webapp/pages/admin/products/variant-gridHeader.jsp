<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	


{title:"<s:message code="label.entity.srn" text="Sr. No."/>", name:"counter", canFilter:false},
{title:"<s:message code="label.entity.variant" text="Pack / Sizes"/>", name:"variant", canFilter:false},	
{title:"<s:message code="label.entity.variantType" text="Pack Type"/>", name:"variantType", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}