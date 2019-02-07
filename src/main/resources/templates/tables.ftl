<#import "parts/common.ftl" as c>
<#import "parts/connect.ftl" as con>

<@c.page>
<#if connect>

<div>Table list</div>
<table>
    <thead>
    <tr>
        <th>Table name</th>
        <th>formated</th>
    </tr>
    </thead>
    <#if tables??>
        <#list tables as table>
        <tbody>
        <tr>
            <td>${table}</td>
            <td><a href="/tables/${table}">edit</a></td>
        </tr>
        </tbody>
        </#list>
    </#if>
</table>

<@con.disconnect/>
<#else>
<@con.connect/>
</#if>

</@c.page>