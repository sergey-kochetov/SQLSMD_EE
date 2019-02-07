<#macro connect>
<form action="/tables/connect" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <label class="col-sm-2 col-form-label">Database :</label>
    <div class="col-sm-4">
        <input type="text" name="database" class="form-control" placeholder="database"/>
    </div>
    <label class="col-sm-2 col-form-label">User name :</label>
    <div class="col-sm-4">
        <input type="text" name="username" class="form-control" placeholder="user"/>
    </div>
    <label class="col-sm-2 col-form-label">Password :</label>
    <div class="col-sm-4">
        <input type="password" name="password" class="form-control" placeholder="pass"/>
    </div>
    <button class="btn btn-primary" type="submit">Connect</button>
</form>
</#macro>

<#macro disconnect>
<form action="/tables/disconnect" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">disconnect</button>
</form>
</#macro>