<#import "parts/common.ftl" as c>
<#import "parts/addTable.ftl" as at>
<@c.page>
<div class=container">
    <p>Table list</p>
    <table class="table">
        <thead>
        <tr>
            <#list head as row>
                <td>${row} <@at.delColum table row/></td>
            </#list>
        </tr>
        </thead>

        <#if datas??>
            <tbody>
            <#list datas as col>
                <tr>
                    <#if col??>
                    <#list col as data>
                        <td>${data}</td>
                    </#list>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </#if>
    </table>
</div>
<@at.addColum table />
<td><a href="/tables">back</a></td>
</@c.page>