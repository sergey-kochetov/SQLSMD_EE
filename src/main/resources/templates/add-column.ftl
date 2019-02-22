<#import "parts/common.ftl" as c>
<#import "parts/addTable.ftl" as at>

<@c.page>
${message?ifExists}

<@at.addTable />
</@c.page>