import { Modal, message } from 'antd';
import loader from '@monaco-editor/loader';
import Editor from '@monaco-editor/react';
import { useEffect, useRef, useState } from 'react';
import { ProFormInstance } from '@ant-design/pro-form';
import './index.less';
import { updateLiteFlowScript } from '@/services/jlc-bot/liteflowScript';
import { getHighLevelDevInfo } from '@/services/jlc-bot/gaojikaifa';
loader.config({ paths: { vs: 'https://cdn.staticfile.org/monaco-editor/0.43.0/min/vs' } });

function EditScript(props) {

  const [cureentContext, setCureentContext] = useState<string>('');

  const [cureentScript, setCureentScript] = useState<API.LiteFlowScript>();

  const [successVisible, setSuccessVisible] = useState<boolean>(false);

  const formRef = useRef<ProFormInstance>();


  const handleEditorChange = (value: string, event: any) => {
    setCureentContext(value);
  };

  useEffect(() => {
    if (props.visible) {
      getHighLevelDevInfo({id:props.editNodeScriptId }).then((res) => {
        if(res.success && res.data){
          setCureentScript(res.data.script);
          setCureentContext(res.data.script.scriptData);
          setSuccessVisible(true);
          message.success("加载脚本成功");
        }else{
          message.warning("加载失败,未找到脚本");
          setSuccessVisible(false);
          props.handleVisible(false);
        }
      });
    }
  }, [props?.editFormScriptId,props.visible]);

  return (
    <Modal
      title="脚本配置"
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
            defaultLanguage={cureentScript?.scriptLanguage}
          />
    </Modal>
  );
}

export default EditScript;
