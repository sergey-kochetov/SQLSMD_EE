<#import "parts/common.ftl" as c>
<#import "parts/addTable.ftl" as at>
<#import "parts/addData.ftl" as ad>

<@c.page>
<div class=container">
    <p>Table list <a class="btn btn-primary" href="/tables">back</a></p>
    <table class="table">
        <thead>
        <tr>
            <#list head as row>
            <td>${row} <#if row=="id"><#else><@at.delColum table row/></#if></td>
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
                        <td><@ad.delData table col /></td>
                </tr>
            </#list>
            </tbody>
        </#if>
    </table>
</div>
<td><a class="btn btn-primary" href="/tables/${table}/add-data">Add data</a></td>
<@at.addColum table />
</@c.page>