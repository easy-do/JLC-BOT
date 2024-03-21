import ProForm, { BetaSchemaForm, ProFormInstance, ProFormTextArea } from "@ant-design/pro-form";
import { Modal } from "antd";
import { useEffect, useRef, useState } from "react";

function EditNodeData(props) {

  const [columnsData, setColumnsData] = useState([]);
  const [confData, setConfData] = useState({});

  const formRef = useRef<ProFormInstance>();

  useEffect(() => {
    const formData = props.sysNode?.formData;
    if(formData){
      try{
        const jsonData = JSON.parse(formData);
        setColumnsData(jsonData);
      }catch{
        setColumnsData([]);
      }
    }else{
      setColumnsData([]);
    }
    const data = props.nodeConf[props.node?.getProp('id')];
    if(data){
      setConfData(data);
    }else{
      setConfData({});
    }

  } , [JSON.stringify(props.node)])

    return(
        <Modal
          title="参数配置"
          open={props.visible}
          destroyOnClose
          onOk={() => {
            console.log(props.nodeConf);
            let conf = props.nodeConf;
            conf[props.node.getProp('id')] = formRef.current?.getFieldsValue();
            props.setNodeConf(conf);
            console.log(props.nodeConf);
            props.handleVisible(false);

          }}
          onCancel={() => {
            props.handleVisible(false);
          }}
        >
         <BetaSchemaForm<any>
            initialValues={confData}
            formRef={formRef}
            shouldUpdate={(newValues, oldValues) => {
              if (newValues.title !== oldValues?.title) {
                return true;
              }
              return false;
            }}
            layoutType="Form"
            submitter={{
              submitButtonProps:{hidden:true},
              resetButtonProps:{hidden:true}
            }}
            columns={columnsData}
        />
        </Modal>
        
    );
}

export default EditNodeData;