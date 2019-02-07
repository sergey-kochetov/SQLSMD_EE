<#import "parts/common.ftl" as c>

<@c.page>
<div>Table list</div>
<table>
    <#list head as row>
        <thead>
            <tr><td>${row}</td></tr>
        </thead>
    </#list>

    <#if datas??>
        <#list datas as col>
        <tbody>
        <tr>
            <#if col??>
                <#list col as data>
                    <td>${data}</td>
                </#list>
            </#if>
        </tr>
        </tbody>
        </#list>
    </#if>
</table>
<td><a href="/tables">back</a></td>
</@c.page>