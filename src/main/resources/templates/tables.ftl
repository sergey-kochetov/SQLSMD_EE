<#import "parts/common.ftl" as c>
<#import "parts/connect.ftl" as con>

<@c.page>
<#if connect>

<div class=container">
    <p>Table list</p>
    <p><a href="/tables/add-table">add new table</a></p>
<table class="table">
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
</div>
<@con.disconnect/>
<#else>
<@con.connect/>
</#if>

</@c.page>