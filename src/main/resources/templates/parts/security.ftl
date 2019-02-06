<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>
<#if know>
    <#assign
        customer =  Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = customer.getUsername()
        isAdmin = customer.getAuthorities()?seq_contains('ADMIN')
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
    >
</#if>