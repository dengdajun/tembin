<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/23
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<link rel="stylesheet" href="<c:url value ="/js/ajaxFileUpload/zyupload/css/jquery.fileupload.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value ="/js/ajaxFileUpload/zyupload/css/jquery.fileupload-ui.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value ="/js/ajaxFileUpload/zyupload/css/blueimp-gallery.min.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value ="/js/ajaxFileUpload/zyupload/css/bootstrap.min.css" />" type="text/css">

<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.ui.widget.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/tmpl.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/load-image.all.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/canvas-to-blob.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/bootstrap.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.blueimp-gallery.min.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.iframe-transport.js" />  ></script>

<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-process.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-image.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-audio.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-video.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-validate.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/jquery.fileupload-ui.js" />  ></script>
<script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/zyupload/main.js" />  ></script>
<%--<script type="text/javascript" src=<c:url value ="/js/item/addItem.js" />  />--%>

<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    function closedialog(){
        W.refreshTable();
        W.pictureManager.close();
    }
</script>
<body>

</div>
<div class="container">
    <br>
    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action="//jquery-file-upload.appspot.com/" method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <noscript><input type="hidden" name="redirect" value="https://blueimp.github.io/jQuery-File-Upload/"></noscript>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Add files...</span>
                    <input type="file" name="multipartFiles" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start upload</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The global file processing state -->
                <span class="fileupload-process"></span>
            </div>
            <!-- The global progress state -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress state -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
    </form>
    <br>

</div>

<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<input type="hidden" value="${sku}" id="sku">
</body>

</html>