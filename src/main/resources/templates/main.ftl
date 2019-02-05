<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout/>
</div>
<div>
    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="text" placeholder="Enter msg">
        <input type="text" name="tag" placeholder="tag">
        <button type="submit">add</button>
    </form>
</div>
<div>List msg</div>
<form method="get" action="/main">
    <input type="text" name="filter" value="${filter?ifExists}">
    <button type="submit">find</button>
</form>
<#list messages as message>
<div>
    <b>${message.id}</b>
    <b>${message.text}</b>
    <b>${message.tag}</b>
    <span>${message.author}</span>
</div>
<#else>
No message
</#list>

</@c.page>