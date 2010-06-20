<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>API Usage</title>
        <!-- Import for JQuery -->
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <!-- Import for SyntaxHighlighter -->
        <link type="text/css" rel="stylesheet" href="syntaxHighlighter/Styles/SyntaxHighlighter.css" />
        <script language="javascript" src="syntaxHighlighter/Scripts/shCore.js"></script>
        <script language="javascript" src="syntaxHighlighter/Scripts/shBrushJScript.js"></script>
        <script language="javascript" src="syntaxHighlighter/Scripts/shBrushXml.js"></script>


        <script>
            //Basic variable instantiation
            var host="localhost";
            var port= <%= request.getLocalPort()%>;
            var contextPath= "<%= request.getContextPath()%>" ;
            var basicURL = "http://"+host+":"+port+contextPath+"/";
            var xmlStart='<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Header/><S:Body>';
            var xmlEnd='</S:Body></S:Envelope>';
            // The following JavaScript functions are used to gather the requiered data from the page, make requests for each service and to display the result.
           // The first six functions are commented, the following are build up similiar to the last ones

            //The SOAP and WebApi Calls
            function makeSOAPCall(soapEnv, URLextension){
                //get basic URL and add extension (special WebService)
                $("#URL").html(basicURL+URLextension);
                //Create code Field:
                start='<pre name="code" id="xmlcode"class="xml">';
                end='</pre>'
                //write request data in DataField
                $("#DataField").html(start+soapEnv+end);
                //make Ajax Call
                $.ajax({
                    url: basicURL+URLextension,
                    type: "POST",
                    dataType: "xml",
                    data: soapEnv,
                    complete: showXmlResult,
                    contentType: "text/xml; charset=\"utf-8\""
                });
            }
            function makeWebAPICall(parameter, action){
                //get basic URL and add extension (special WebService)
                $("#URL").html(basicURL+"WebAPI?action="+action);
                //Create code field:
                start='<pre name="code" id="xmlcode"class="xml">';
                end='</pre>';
                //write request data in DataField
                $("#DataField").html(start+parameter+end);
                //make Ajax Call
                $.ajax({
                    url: basicURL+"WebAPI?action="+action+"&"+parameter,
                    type: "GET",
                    dataType: "json",
                    complete: showJsonResult,
                    contentType: "text/xml; charset=\"utf-8\""
                });
            }
            <!--The Callbackfunctions for the SOAP and WebApi Calls-->
            function showXmlResult(result,status){
                //Create code field
                start='<pre name="code" id="xmlcode"class="xml">';
                end='</pre>';
                // Write Result in code field
                $("#Code").html(start+result.responseText+end);
                // Highlight all code fields
                dp.SyntaxHighlighter.HighlightAll('code');
            }
            
            function showJsonResult(result,status){
                //Create code field
                start='<pre name="code" id="javascriptcode"class="jscript">';
                end='</pre>';
                // Write Result in code field
                $("#Code").html(start+result.responseText+end);
                // Highlight all code fields
                dp.SyntaxHighlighter.HighlightAll('code');
            }


            <!--Maintenance services-->
            function setUpDB(){
                //create SOAP message:
                var soapEnv =xmlStart+'<ns2:setUpDB xmlns:ns2="http://ws.wwu.de/"/>'+xmlEnd;
                //select Service type (WebAPI or WebService
                if($("#callChooser").val()=="1"){
                    //make WebAPI Call
                    makeWebAPICall("", "setUpDB");
                }else{
                    //Make WebService Call
                    makeSOAPCall(soapEnv, "MaintenanceService");
                }
            }

            <!--Domain services-->
            function createDomain(){
                //get sending parameters
                domain_name=$("#createDomainName").val();
                var soapEnv=xmlStart+'<ns2:createDomain xmlns:ns2="http://ws.wwu.de/"><domainname>'+domain_name+'</domainname></ns2:createDomain>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="domainName="+domain_name;
                    makeWebAPICall(sendParameters, "createDomain");
                }else{
                    makeSOAPCall(soapEnv, "DomainService");
                }
            }

            function listDomain(){
                var soapEnv=xmlStart+'<ns2:listDomains xmlns:ns2="http://ws.wwu.de/"/>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    makeWebAPICall("", "listDomains");
                }else{
                    makeSOAPCall(soapEnv, "DomainService");
                }
            }
            function deleteDomain(){
                domain_name=$("#deleteDomainName").val();
                var soapEnv=xmlStart+'<ns2:deleteDomain xmlns:ns2="http://ws.wwu.de/"><domainname>'+domain_name+'</domainname></ns2:deleteDomain>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="domainName="+domain_name;
                    makeWebAPICall(sendParameters, "deleteDomain");
                }else{
                    makeSOAPCall(soapEnv, "DomainService");
                }
            }

            <!--Attribute services-->

            function createDbAttribute(){
                domain_name=$("#createAttributeDomainName").val();
                attribute_name=$("#createAttributeName").val();
                var soapEnv=xmlStart+'<ns2:createAttribute xmlns:ns2="http://ws.wwu.de/"><attributeName>'+attribute_name+'</attributeName><domainName>'+domain_name+'</domainName></ns2:createAttribute>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="attributeName="+attribute_name+"&"+"domainName="+domain_name;
                    makeWebAPICall(sendParameters, "createAttribute");
                }else{
                    makeSOAPCall(soapEnv, "AttributeService");
                }
            }
            function listDbAttributes(){
                domain_name=$("#listAttributeDomainName").val();
                var soapEnv=xmlStart+'<ns2:listDomainAttributes xmlns:ns2="http://ws.wwu.de/"><domainName>'+domain_name+'</domainName></ns2:listDomainAttributes>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="domainName="+domain_name;
                    makeWebAPICall(sendParameters, "listDomainAttributes");
                }else{
                    makeSOAPCall(soapEnv, "AttributeService");
                }
            }
            function deleteDbAttribute(){
                domain_name=$("#deleteAttributeDomainName").val();
                attribute_name=$("#deleteAttributeName").val();
                var soapEnv=xmlStart+'<ns2:deleteAttribute xmlns:ns2="http://ws.wwu.de/"><attributeName>'+attribute_name+'</attributeName><domainName>'+domain_name+'</domainName></ns2:deleteAttribute>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="attributeName="+attribute_name+"&"+"domainName="+domain_name;
                    makeWebAPICall(sendParameters, "deleteAttribute");
                }else{
                    makeSOAPCall(soapEnv, "AttributeService");
                }
            }
            <!--Item services-->
            function createItem(){
                domain_name=$("#createItemDomainName").val();
                var soapEnv=xmlStart+'<ns2:createItem xmlns:ns2="http://ws.wwu.de/"><domainName>'+domain_name+'</domainName></ns2:createItem>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="domainName="+domain_name;
                    makeWebAPICall(sendParameters, "createItem");
                }else{
                    makeSOAPCall(soapEnv, "ItemService");
                }
            }

            function listItems(){
                domain_name=$("#listItemDomainName").val();
                var soapEnv=xmlStart+'<ns2:listDomainItems xmlns:ns2="http://ws.wwu.de/"><domainName>'+domain_name+'</domainName></ns2:listDomainItems>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="domainName="+domain_name;
                    makeWebAPICall(sendParameters, "listDomainItems");
                }else{
                    makeSOAPCall(soapEnv, "ItemService");
                }
            }
            function deleteItem(){
                item_id=$("#deleteItemId").val();
                var soapEnv=xmlStart+'<ns2:deleteItem xmlns:ns2="http://ws.wwu.de/"><itemId>'+item_id+'</itemId></ns2:deleteItem>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="itemId="+item_id;
                    makeWebAPICall(sendParameters, "deleteItem");
                }else{
                    makeSOAPCall(soapEnv, "ItemService");
                }
            }

            function setItemValue(){
                item_id=$("#setItemItemId").val();
                value=$("#setItemValue").val();
                attribute_name=$("#setItemAttributeName").val();
                var soapEnv=xmlStart+'<ns2:setItemValue xmlns:ns2="http://ws.wwu.de/">\
            <itemId>'+item_id+'</itemId>\
            <attributeName>'+attribute_name+'</attributeName>\
            <value>'+value+'</value>\
        </ns2:setItemValue>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="itemId="+item_id+"&"+"value="+value+"&"+"attributeName="+attribute_name;
                    makeWebAPICall(sendParameters, "setItemValue");
                }else{
                    makeSOAPCall(soapEnv, "ItemService");
                }
            }

            function getItemValue(){
                item_id=$("#getItemItemId").val();
                attribute_name=$("#getItemAttributeName").val();
                var soapEnv=xmlStart+'<ns2:getItemValue xmlns:ns2="http://ws.wwu.de/"><itemId>'+item_id+'</itemId><attributeName>'+attribute_name+'</attributeName></ns2:getItemValue>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="itemId="+item_id+"&"+"attributeName="+attribute_name;
                    makeWebAPICall(sendParameters, "getItemValue");
                }else{
                    makeSOAPCall(soapEnv, "ItemService");
                }
            }

            <!--Search services-->
            function searchByValue(){
                value=$("#searchValue1").val();
                var soapEnv=xmlStart+'<ns2:search xmlns:ns2="http://ws.wwu.de/"><value>'+value+'</value></ns2:search>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="value="+value;
                    makeWebAPICall(sendParameters, "listSearchItems");
                }else{
                    makeSOAPCall(soapEnv, "SearchService");
                }
            }

            function searchByValueAndDomainName(){
                value=$("#searchValue2").val();
                domain_name=$("#searchDomainName2").val();
                var soapEnv=xmlStart+'<ns2:search2 xmlns:ns2="http://ws.wwu.de/"><value>'+value+'</value><domainName>'+domain_name+'</domainName></ns2:search2>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="value="+value+"&"+"domainName="+domain_name;
                    makeWebAPICall(sendParameters, "listSearchItems");
                }else{
                    makeSOAPCall(soapEnv, "SearchService");
                }
            }
            
            function searchByValueAndDomainNameAndAttributeName(){
                value=$("#searchValue3").val();
                domain_name=$("#searchDomainName3").val();
                attribute_name=$("#searchAttributeName3").val();
                var soapEnv=xmlStart+'<ns2:search3 xmlns:ns2="http://ws.wwu.de/"><value>'+value+'</value><domainName>'+domain_name+'</domainName><attributeName>'+attribute_name+'</attributeName></ns2:search3>'+xmlEnd;
                if($("#callChooser").val()=="1"){
                    sendParameters="value="+value+"&"+"domainName="+domain_name+"&"+"attributeName="+attribute_name;
                    makeWebAPICall(sendParameters, "listSearchItems");
                }else{
                    makeSOAPCall(soapEnv, "SearchService");
                }
            }





        </script>
        <script type="text/javascript">
            // Initialize the Accordions and Tabs
            $(function() {
                $("#accordion0").accordion({ header: "h3" });
                $("#accordion1").accordion({ header: "h3" });
                $("#accordion2").accordion({ header: "h3" });
                $("#accordion3").accordion({ header: "h3" });
                $("#accordion4").accordion({ header: "h3" });
                $("#tabs").tabs();
                

            });
        </script>

    </head>
    <body>

        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Maintenance</a></li>
                <li><a href="#tabs-2">Domain</a></li>
                <li><a href="#tabs-3">Attribute</a></li>
                <li><a href="#tabs-4">Item</a></li>
                <li><a href="#tabs-5">Search</a></li>

            </ul>
            <hr>
            <h2><center>Action:</center></h2>
            <div id="tabs-1">
                <div id="accordion0">
                    <div>
                        <h3><a href="#">Setup</a></h3>
                        <div> <input type="submit" value="setUpDB()" onClick="setUpDB();"/></div>
                    </div>
                </div>

            </div>
            <div id="tabs-2">
                <div id="accordion1">
                    <div>
                        <h3><a href="#">Create</a></h3>
                        <div>domain_name: <input type="text" name="createDomainName" id="createDomainName" value="" /><input type="submit" value="createDomain()"  onclick="createDomain();" /></div>
                    </div>
                    <div>
                        <h3><a href="#">List</a></h3>
                        <div><input type="submit" value="listDomains()" onclick="listDomain();"/></div>
                    </div>
                    <div>
                        <h3><a href="#">Delete</a></h3>
                        <div>domain_name: <input type="text" id="deleteDomainName" name="deleteDomainName" value="" /><input type="submit" value="deleteDomain()" onclick="deleteDomain();" /></div>
                    </div>
                </div>

            </div>
            <div id="tabs-3">
                <div id="accordion2">
                    <div>
                        <h3><a href="#">Create</a></h3>
                        <div>attribute_name: <input type="text" name="createAttributeName" id="createAttributeName" value="" />, domain_name: <input type="text" name="createAttributeDomainName" value="" id="createAttributeDomainName"/><input type="submit" value="createAttribute()" onClick="createDbAttribute();" /></div>
                    </div>
                    <div>
                        <h3><a href="#">List</a></h3>
                        <div>domain_name: <input type="text" name="listAttributeDomainName" value="" id="listAttributeDomainName"/><input type="submit" value="listAttributes()" onClick="listDbAttributes();"/></div>
                    </div>
                    <div>
                        <h3><a href="#">Delete</a></h3>
                        <div>attribute_name: <input type="text" name="deleteAttributeName" value="" id="deleteAttributeName"/>, domain_name: <input type="text" name="deleteAttributeDomainName" value="" id="deleteAttributeDomainName"/><input type="submit" value="deleteAttribute()" onClick="deleteDbAttribute();"/></div>
                    </div>
                </div>
            </div>
            <div id="tabs-4">
                <div id="accordion3">
                    <div>
                        <h3><a href="#">Create</a></h3>
                        <div>domain_name: <input type="text" id="createItemDomainName" value="" /><input type="submit" value="createItem()" onclick="createItem();"/></div>
                    </div>
                    <div>
                        <h3><a href="#">List</a></h3>
                        <div>domain_name: <input type="text" id="listItemDomainName" value="" /><input type="submit" value="listDomainItems()" onclick="listItems();"/></div>
                    </div>
                    <div>
                        <h3><a href="#">Delete</a></h3>
                        <div>item_id: <input type="text" id="deleteItemId" value="" /><input type="submit" value="deleteItem()" onclick="deleteItem();"/></div>
                    </div>
                    <div>
                        <h3><a href="#">SetValue</a></h3>
                        <div>item_id: <input type="text" id="setItemItemId" value="" />, attribute_name: <input type="text" id="setItemAttributeName" value="" />, value: <input type="text" id="setItemValue" value="" /><input type="submit" value="setItemValue()" onclick="setItemValue();" /></div>
                    </div>
                    <div>
                        <h3><a href="#">GetValue</a></h3>
                        <div>item_id: <input type="text" id="getItemItemId" value="" />, attribute_name: <input type="text" id="getItemAttributeName" value="" /><input type="submit" value="getItemValue()" onclick="getItemValue();" /></div>
                    </div>
                </div>
            </div>
            <div id="tabs-5">
                <div id="tabs-3">
                    <h3>Search by…</h3>
                    <div id="accordion4">
                        <div>
                            <h3><a href="#">…value</a></h3>
                            <div>value: <input type="text" name="searchValue1" id="searchValue1" value="" /><input type="submit" value="search()" onclick="searchByValue();" /></div>
                        </div>
                        <div>
                            <h3><a href="#">…value AND domain_name</a></h3>
                            <div>value: <input type="text" name="searchValue2" id="searchValue2" value="" />, domain_name: <input type="text" name="searchDomainName2" id="searchDomainName2" value="" /><input type="submit" value="search()" onclick="searchByValueAndDomainName();" /></div>
                        </div>
                        <div>
                            <h3><a href="#">…value AND domain_name AND attribute_name</a></h3>
                            <div>value: <input type="text" name="searchValue3" id="searchValue3" value="" />, domain_name: <input type="text" name="searchDomainName3" id="searchDomainName3" value="" />, attribute_name: <input type="text" name="searchAttributeName3" id="searchAttributeName3" value="" /><input type="submit" value="search()" onclick="searchByValueAndDomainNameAndAttributeName();" /></div>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <center><h2>Request:</h2>
                Please choose service type:
                <select name="choose" id="callChooser">
                    <option value="1">WebAPI</option>
                    <option value="2">WebService</option>
                </select>
            </center>
            <strong>URL:</strong><div id="URL"></div>
            <strong>Data:</strong><div id="DataField"></div>
            <hr>
            <center> <h2>Result:</h2></center>
            <div id="Code"></div>
        </div>

    </body>
</html>
