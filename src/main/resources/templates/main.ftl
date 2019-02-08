<#import "parts/common.ftl" as c>

<@c.page>
<div>List msg</div>
<div class="form-row">
    <div class="form-group col-md-4">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" value="${filter?ifExists}" placeholder="find">
            <button type="submit" class="btn btn-primary ml-1">find</button>
        </form>
    </div>
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false">
    Add feedback
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div class="form-group">
            <input type="text" class="form-control"
                   value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Title">
                <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
                </#if>
            </div>

            <div class="form-group">
            <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                   value="<#if message??>${message.text}</#if>" name="text" placeholder="Enter msg">
                <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
                </#if>
            </div>

            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Add file</label>
            </div>

            <button type="submit" class="btn btn-primary">Add</button>
        </form>
    </div>
</div>

<div class="card">
    <#list messages as message>
    <div class="card-header my-2">
        <b>${message.tag}</b>
    </div>
    <div class="card-body my-2">
        <blockquote class="blockquote mb-1">
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
                <span>${message.text}</span>
                <footer class="blockquote-footer">Say <cite title="Source Title"><i>${message.author}</i></cite></footer>
        </blockquote>
    </div>
    <#else>
    No message
    </#list>
</div>

</@c.page>