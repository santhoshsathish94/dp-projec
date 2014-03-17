<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<div class="tabbable">
	<jsp:include page="/common/adminTabs.jsp" />
	<h3>
		<s:message code="label.company.inventoryManagement.title" text="" />
	</h3>
	<br /> <br />

	
</div>
<head>
   <link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>

</head>
<script type="text/javascript">
    function LogFunction(str) {
        console.log(str);
    }
    function addRow(tableForAppend) {
        LogFunction("addRow");
        var row = document.createElement('tr');
        tableForAppend.appendChild(row);
 
        return row;
    }
    function rowCount(tableForAppend) {
        LogFunction("rowCount");
        var iCount = 0;
        for (var i = 0, row; row = tableForAppend.rows[i]; i++) {
            iCount++;
        }
        return iCount;
    }
    function createDynFlowTblCell(row,stitle) {
        nc = nextCell(row);
        s = document.createElement('b');
        s.textContent = stitle;
       // s.className = 'ui-table-cell-label';
        nc.appendChild(s);
        return nc;
    }
    function createDynFlowFirstTblCell(row, title) {
        theTh = document.createElement("th");
        s = document.createElement('b');
        s.textContent = title;
       // s.className = 'ui-table-cell-label';
        theTh.appendChild(s);
        return theTh;
    }
    function addInputs(tableForAppend, row) {
        LogFunction("addInputs");
        idNum = rowCount(tableForAppend);
 
 
      
        theTh = createDynFlowFirstTblCell(row, '');
        theTh.appendChild(createTextElement("", "Qty" + idNum))
        row.appendChild(theTh);
 
        nc = createDynFlowTblCell(row, '');
       
        nc.appendChild(createTextElement("", "ss" + idNum));
 
 
        nc = createDynFlowTblCell(row, '');
       
        nc.appendChild(createTextElement("", "rr" + idNum));
 
        nc = createDynFlowTblCell(row, '');
      
        nc.appendChild(createTextElement("", "" + idNum));
 
        nc = createDynFlowTblCell(row, '');
     
        nc.appendChild(createTextElement("", "" + idNum));
        
       
    }
    function nextCell(tableRow) {
        LogFunction("nextCell");
        var cell = document.createElement('td');
        tableRow.appendChild(cell);
        return cell;
    }
    function createTextElement(sValue, sId) {
        LogFunction("createTextElement");
        element = document.createElement("input");
        element.type = "text";
        element.value = sValue;
        element.name = sId;
        element.id = sId;
        element.onchange = function () { freightclass(this); };
        element.onkeydown = function (evt) { NumbersOnly(evt) };
 
        $(element).addClass("dynText");
 
        return element;
    }
 
 
    function addTableRow() {
        LogFunction("addTableRow");
        table = $('#movie-table')[0];
        table = table.tBodies[0]
 
        r = addRow(table);
        addInputs(table, r);
 
        $('.dynButton').button();
        $('.dynButton').buttonMarkup({ mini: true })
        $('.dynText').attr("data-mini", "true");
        
        $('.dynText').textinput();
        
        //$('#content - primary').trigger("create");
        $('#movie-table').table();//"refresh"
 
    }
</script> 
<body> 

    <div id="forTable">
    <div style="float: left; margin-top: 10px;  width: 100%;">
					
		<div class="control-group" style="float: left;">
			<div style="float: left; width: 100px;">
                <label><s:message code="label.accountingeriod.fromDate" text="Date"/></label>
			</div>
			<div style="float: left; width: 200px;"  class="controls">
				<input id="fromSDate" name="fromSDate" value="${accountingPeriod.fromSDate}" style="width: 150px;" class="small" type="text" data-date-format="<%=com.salesmanager.core.constants.Constants.DEFAULT_DATE_FORMAT%>" data-datepicker="datepicker"> 
				<span class="help-inline"><form:errors path="fromSDate" cssClass="error" /></span>
			</div>
			
			<div style="float: left; width: 100px;">
                <label><s:message code="label.accountingeriod.fromDate" text="Comments"/></label>
			</div>
			<div style="float: left; width: 200px;"  class="controls">
				<input id="fromSDate" name="fromSDate" value="${accountingPeriod.fromSDate}" style="width: 150px;" class="small" type="text"> 
				<span class="help-inline"><form:errors path="fromSDate" cssClass="error" /></span>
			</div>
			
		</div>
	</div>
    <table data-role="table" id="movie-table" data-mode="reflow" class="ui-responsive table-stroke ui-responsive">
      <thead>
        <tr>
          <th data-priority="1" width="40%">Product</th>
          <th data-priority="persist" width="10%">Quantity</th>
          <th data-priority="2" width="10%">UOM</th>
          <th data-priority="3" width="20%"><abbr title="Rotten Tomato Rating">Unit Price</abbr></th>
          <th data-priority="4" width="20%">Amount</th>
        </tr>
      </thead>
      <tbody>
                
      </tbody>
    </table>
    </div>
  <input type="button"id="test" onclick="addTableRow();" value="add" />

</body>

<script>
//addTableRow();

</script>