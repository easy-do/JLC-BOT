import { ModalForm, ProFormText } from "@ant-design/pro-form";



function EditEdgeData(props) {

    return(
        <ModalForm
          modalProps={{
            destroyOnClose: true,
          }}
          style={{ height: '30%', width: '30%' }}
          title="编辑连线"
          visible={props.visible}
          onVisibleChange={props.handleVisible}
          onFinish={async (value) => {
            props.edgeData.setLabels(value.name);
            props.handleVisible(false);
          }}
        >
          <ProFormText
            name='name'
            label='连线名称'
          />
        </ModalForm>
    );
}

export default EditEdgeData;