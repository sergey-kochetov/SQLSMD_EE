<#import "parts/common.ftl" as c>

<@c.page>
<div>Table list</div>
<table>
    <thead>
    <tr>
        <th>Table name</th>
        <th>formated</th>
    </tr>
    </thead>
    <#list tables as table>
    <tbody>
    <tr>
        <td>${table}</td>
        <td><a href="/tables/${table}">edit</a></td>
    </tr>
    </tbody>
</#list>
</table>
</@c.page>