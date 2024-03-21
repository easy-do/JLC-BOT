import { Modal, message } from 'antd';
import loader from '@monaco-editor/loader';
import Editor from '@monaco-editor/react';
import { useEffect, useRef, useState } from 'react';
import { BetaSchemaForm, ProFormInstance } from '@ant-design/pro-form';
import './index.less';
import { updateSysNode } from '@/services/jlc-bot/lowCodeSysNodeController';
loader.config({ paths: { vs: 'https://cdn.staticfile.org/monaco-editor/0.43.0/min/vs' } });

function EditNodeForm(props) {
  const [cureentContext, setCureentContext] = useState<string>('[]');

  const [columnsData, setColumnsData] = useState([]);
  const formRef = useRef<ProFormInstance>();

  const handleEditorChange = (value: string, event: any) => {
    setCureentContext(value);
    try {
      setColumnsData(JSON.parse(value));
    } catch {
      setColumnsData([]);
    }
  };

  useEffect(() => {
    if (props.currentRow) {
      const formData = props.currentRow.formData;
      if (formData) {
        setCureentContext(formData);
        try {
          setColumnsData(JSON.parse(formData));
        } catch {
          setColumnsData([]);
        }
      } else {
        setCureentContext('[]');
        setColumnsData([]);
      }
    }
  }, [props?.currentRow]);

  return (
    <Modal
      title="表单配置"
      open={props.visible}
      destroyOnClose
      width={'80%'}
      onOk={() => {
        updateSysNode({ id: props.currentRow.id, formData: cureentContext }).then((res) => {
          if (res.success) {
            message.success(res.message);
          } else {
            message.warning(res.errorMessage);
          }
        });
      }}
      onCancel={() => {
        props.handleVisible(false);
      }}
    >
      <div className="EditForm">
        <div className="formcode">
          <Editor
            height="65vh"
            theme={'vs-dark'}
            width="70%"
            defaultValue={cureentContext}
            onChange={handleEditorChange}
            defaultLanguage="json"
          />
        </div>
        <div className="formveiw">
          表单预览
          <BetaSchemaForm<any>
            formRef={formRef}
            shouldUpdate={(newValues, oldValues) => {
              if (newValues.title !== oldValues?.title) {
                return true;
              }
              return false;
            }}
            layoutType="Form"
            submitter={{
              submitButtonProps: { hidden: true },
              resetButtonProps: { hidden: true },
            }}
            columns={columnsData}
          />
        </div>
      </div>
    </Modal>
  );
}

export default EditNodeForm;
