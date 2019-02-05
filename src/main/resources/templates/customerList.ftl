<#import "parts/common.ftl" as c>

<@c.page>
<div>List user</div>
<table>
    <thead>
    <tr>
        <th>name</th>
        <th>role</th>
    </tr>
    </thead>
    <#list customers as customer>
    <tbody>
    <tr>
        <td>${customer.username}</td>
        <td><#list customer.roles as role>${role}<#sep>, </#list></td>
        <td><a href="/customer/${customer.id}">edit</a></td>
    </tr>
    </tbody>
</#list>
</table>

</@c.page>