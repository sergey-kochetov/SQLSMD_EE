<#import "parts/common.ftl" as c>

<@c.page>
<div>User editor</div>
<form action="/customer" method="post">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <input type="hidden" value="${customer.id}" name="customerId">
    <input type="text" value="${customer.username}" name="username">
    <#list roles as role>
    <div>
        <label><input type="checkbox" name="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
    </#list>
    <button type="submit">save</button>
</form>
</@c.page>