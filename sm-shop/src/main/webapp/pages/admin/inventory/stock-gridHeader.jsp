<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.id" text="Id"/>", name:"stockId", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockSKU" text="Product"/>", name:"stockSKU", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockQuantity" text="Quantity"/>", name:"stockQuantity", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockUOM" text="UOM"/>", name:"stockUOM", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockUnitPrice" text="Unit Price"/>", name:"stockUnitPrice", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockAmount" text="Amount"/>", name:"stockAmount", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockSDate" text="Date"/>", name:"stockSDate", canFilter:false}