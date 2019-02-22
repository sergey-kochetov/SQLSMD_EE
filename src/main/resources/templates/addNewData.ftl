<#import "parts/common.ftl" as c>
<#import "parts/addData.ftl" as ad>

<@c.page>
<div class=container">
    <p>Add new data<a class="btn btn-primary" href="/tables/${table}">back</a></p>
    <table class="table">
        <thead>

        </thead>
        <tbody>

        </tbody>
    </table>
</div>
<@ad.addData table head />

</@c.page>