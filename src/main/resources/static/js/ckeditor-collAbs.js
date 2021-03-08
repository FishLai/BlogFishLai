var myEditor;
InlineEditor.create(document.getElementById("rich-editor"), {
        toolbar: [
            'heading', '|',
            'bold',
            'italic',
            'blockQuote',
            'link',
            'insertTable',
            'undo',
            'redo'
        ],
        placeholder: "可以輸入作品說明..."
    })
    .then(editor => {
        myEditor = editor;
        console.log("hi inline editor")
    });