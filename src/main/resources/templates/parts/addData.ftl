<#macro addData table head>
<form action="/tables/${table}/add-data" method="post">

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#list head as row>
    <label class="col-sm-2 col-form-label">${row} :</label>
    <div class="col-sm-4">
        <input type="text" name="${row}" class="form-control" placeholder="${row}"/>
    </div>
    </#list>
    <button class="btn btn-primary" type="submit">Add</button>
</form>
</#macro>

<#macro delData table col>
<form action="/tables/${table}/del/${col[0]}" method="post">

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

<button class="btn btn-primary" type="submit">del</button>
</form>
</#macro>