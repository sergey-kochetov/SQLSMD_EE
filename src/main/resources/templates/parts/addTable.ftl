<#macro addTable>
<form action="/tables/add-table" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <label class="col-sm-2 col-form-label">Table name :</label>
    <div class="col-sm-4">
        <input type="text" name="tablename" class="form-control" placeholder="tablename"/>
    </div>
    <button class="btn btn-primary" type="submit">Create table</button>
</form>
</#macro>

<#macro addColum tablename>
<form action="/tables/${tablename}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <label class="col-sm-2 col-form-label">add colum :</label>
    <div class="col-sm-4">
        <input type="text" name="columname" class="form-control" placeholder="+"/>
    </div>
    <div class="col-sm-4">
        <input type="text" name="datatype" class="form-control" placeholder="datatype"/>
    </div>
    <button class="btn btn-primary" type="submit">add</button>
</form>
</#macro>

<#macro delColum tablename column>
<form action="/tables/${tablename}/delColum/${column}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">del</button>
</form>
</#macro>
