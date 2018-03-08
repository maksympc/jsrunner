YUI().use(
    'aui-ace-editor',
    function (Y) {
        var editor = new Y.AceEditor(
            {
                boundingBox: '#myEditor',
                mode: 'javascript',
                value: 'alert("Write something here...");',
                width:'400'
            }
        ).render();
    }
);