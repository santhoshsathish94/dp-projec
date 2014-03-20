<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.serial.number" text="SNO."/>", name:"serialNumber", canFilter:false},
{title:"<s:message code="label.entity.party.name" text="Party Name"/>", name:"partyName", canFilter:false},	
{title:"<s:message code="label.generic.item.name" text="Item Name"/>", name:"itemName", canFilter:false},
{title:"<s:message code="label.generic.company.name" text="Company Name"/>", name:"companyName", canFilter:false},
{title:"<s:message code="label.generic.c.d" text="C.D. (&#37;)"/>", name:"cdField", canFilter:false},
{title:"<s:message code="label.generic.c.d" text="ADD. (&#37;)"/>", name:"addField", canFilter:false},
{title:"<s:message code="label.generic.c.d" text="T.D. (&#37;)"/>", name:"tdField", canFilter:false},
{title:"<s:message code="label.generic.c.d" text="RATE"/>", name:"rateField", canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}