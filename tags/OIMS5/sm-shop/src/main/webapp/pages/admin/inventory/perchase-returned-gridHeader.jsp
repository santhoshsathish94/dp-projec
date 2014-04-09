<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>	



{title:"<s:message code="label.entity.id" text="Id"/>", name:"Id", canFilter:false},
{title:"<s:message code="" text="Name (Dabit To)"/>", name:"Supplier", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockSKU" text="Product"/>", name:"SKU", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockQuantity" text="Quantity"/>", name:"Quantity", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockUnitPrice" text="Unit Price"/>", name:"UnitPrice", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockAmount" text="Amount"/>", name:"Amount", canFilter:false},
{title:"<s:message code="label.inventoryManagement.stock.stockSDate" text="Date"/>", name:"SDate", canFilter:false}