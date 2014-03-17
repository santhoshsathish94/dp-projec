
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>


<html>
<head>
    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <link	href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />"rel="stylesheet"></link>
	<script	src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
</head>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="label.company.inventoryManagement.title" text="" />
	</h3>
	<br />
</div>
<div style="float: left; margin-top: 10px; width: 100%;">

		<div class="control-group" style="float: left;">
			<div style="float: left; width: 100px;">
				<label><s:message code="label.accountingeriod.fromDate"
						text="Date" /></label>
			</div>
			<div style="float: left; width: 200px;" class="controls">
				<input id="fromSDate" name="fromSDate"
					value="${accountingPeriod.fromSDate}" style="width: 150px;"
					class="small" type="text"
					data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>"
					data-datepicker="datepicker"> <span class="help-inline"><form:errors
						path="fromSDate" cssClass="error" /></span>
			</div>

			<div style="float: left; width: 100px;">
				<label><s:message code="label.accountingeriod.fromDate"
						text="Comments" /></label>
			</div>
			<div style="float: left; width: 200px;" class="controls">
				<input id="fromSDate" name="fromSDate"
					value="${accountingPeriod.fromSDate}" style="width: 150px;"
					class="small" type="text"> <span class="help-inline"><form:errors
						path="fromSDate" cssClass="error" /></span>
			</div>

		</div>
<div id='container'>
<table >
   <thead>
        <tr>
		  <th><input width="5%"class='check_all' type='checkbox' onclick="select_all()"/></th>
		  <th width="5%">Sr.No</th>
          <th width="40%">Product</th>
          <th width="10%">Quantity</th>
          <th width="10%">UOM</th>
          <th width="15%">Unit Price</th>
          <th width="15%">Amount</th>
        </tr>
      </thead>
      <tbody>
		<tr>
			<td><input width="5%"type='checkbox' class='case'/></td>
			<td width="5%"><span id='snum'>1.</span></td>
			<td width="40%"><input type='text' id='Product' name='Product[]'style="width:98%"/></td>
			<td width="10%"><input type='text' id='Quantity' name='Quantity[]'style="width:98%"/></td>
			<td width="10%"><input type='text' id='UOM' name='UOM[]'style="width:98%"/></td>
			<td width="15%"><input type='text' id='Unit-Price' name='Unit-Price[]'style="width:98%"/> </td>
			<td width="15%"><input type='text' id='Amount' name='Amount[]'style="width:98%"/></td>
			
	  </tr>
      </tbody>
  
</table>
 
<button type="button" class='delete'>- Delete</button>
<button type="button" class='addmore'>+ Add More</button>
<p>
<input type='submit' name='submit' value='submit' class='but'/></p>
</div>
</div>
<script>
$(".delete").on('click', function() {
    $('.case:checkbox:checked').parents("tr").remove();
    $('.check_all').prop("checked", false); 
    check();
 
});
var i=2;
$(".addmore").on('click',function(){
    count=$('table tr').length;
    var data="<tr><td><input type='checkbox' class='case'style='width:98%'/></td><td><span id='snum"+i+"'>"+count+".</span></td>";
    data +="<td><input type='text' id='Product"+i+"' name='Product[]'style='width:98%'/></td> <td><input type='text' id='Quantity"+i+"' name='Quantity[]'style='width:98%'/></td><td><input type='text' id='UOM"+i+"' name='UOM[]'style='width:98%'/></td><td><input type='text' id='Unit-Price"+i+"' name='Unit-Price[]'style='width:98%'/></td><td><input type='text' id='Amount"+i+"' name='Amount[]'style='width:98%'/></td>";
    $('table').append(data);
    i++;
});
 
function select_all() {
    $('input[class=case]:checkbox').each(function(){ 
        if($('input[class=check_all]:checkbox:checked').length == 0){ 
            $(this).prop("checked", false); 
        } else {
            $(this).prop("checked", true); 
        } 
    });
}
 
function check(){
    obj=$('table tr').find('span');
    $.each( obj, function( key, value ) {
    id=value.id;
    $('#'+id).html(key+1);
    });
    }
 
</script>