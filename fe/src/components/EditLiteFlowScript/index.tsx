import { Modal, message } from 'antd';
import loader from '@monaco-editor/loader';
import Editor from '@monaco-editor/react';
import { useEffect, useRef, useState } from 'react';
import { updateLiteFlowScript } from '@/services/jlc-bot/liteflowScript';
loader.config({ paths: { vs: 'https://cdn.staticfile.org/monaco-editor/0.45.0/min/vs' } });



function EditLiteFlowScript(props: { script: API.LiteFlowScript; visible: boolean; handleVisible: (arg0: boolean) => void; }) {

  const [cureentContext, setCureentContext] = useState<string>('');

  const [cureentScript, setCureentScript] = useState<API.LiteFlowScript>();

  const [successVisible, setSuccessVisible] = useState<boolean>(false);

  const editorRef = useRef(null);

  function handleEditorDidMount(editor: any, monaco: any) {
    editorRef.current = editor;
    monaco.languages.registerCompletionItemProvider('java', {
      provideCompletionItems: function(_model: any, __position: any) {
          return {
              suggestions: [
                  { label: 'System.out.println', kind: monaco.languages.CompletionItemKind.Method, insertText: 'System.out.println();' },
              ]
          };
      }
  });
  }

  const handleEditorChange = (value: string, event: any) => {
    setCureentContext(value);
  };

  useEffect(() => {
    if (props.visible) {
        setCureentScript(props.script);
        setCureentContext(props.script.scriptData);
        setSuccessVisible(true);
    }
  }, [props?.script,props.visible]);

  return (
    <Modal
      title="代码编辑"
      open={props.visible && successVisible}
      destroyOnClose
      width={'80%'}
      onOk={() => {
        cureentScript.scriptData = cureentContext;
        updateLiteFlowScript(cureentScript).then(res=>{
          if(res.success && res.data){
            message.success(res.message);
          }else{
            message.warning(res.errorMessage);
          }
        })
      }}
      onCancel={() => {
        props.handleVisible(false);
        setSuccessVisible(false);
      }}
    >
          <Editor
            height="65vh"
            theme={'vs-dark'}
            // width="70%"
            defaultValue={cureentContext}
            onChange={handleEditorChange}
            onMount={handleEditorDidMount}
            defaultLanguage={cureentScript?.scriptLanguage}
          />
    </Modal>
  );
}

export default EditLiteFlowScript;
