<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-4">
            <input type="text" name="username" class="form-control" placeholder="name"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password :</label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control" placeholder="pass"/>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#if !isRegisterForm>
        <a href="/registration">
            <button type="button" class="btn btn-primary">registration</button>
        </a>
    </#if>

    <button class="btn btn-primary" type="submit">
        <#if !isRegisterForm>
            Ok
        <#else>
            Create
        </#if>
    </button>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">Sign Out</button>
</form>
</#macro>