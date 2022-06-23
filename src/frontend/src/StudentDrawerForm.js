import {Button, Col, Drawer, Form, Input, Row, Select, Spin} from "antd";
import {addNewStudent, editStudent} from "./client";
import {LoadingOutlined} from "@ant-design/icons";
import {useEffect, useState} from "react";
import {errorNotification, successNotification} from "./Notification";

const { Option } = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function StudentDrawerForm({
  isRegister,
  showDrawer,
  setShowDrawer,
  fetchStudents,
  selectedStudent,
}) {
  // states
  const onClose = () => {
    form.resetFields();
    setShowDrawer(false);
  };
  const [form] = Form.useForm();
  const [submitting, setSubmitting] = useState(false);

  // every time open this drawer, will be invoked.
  useEffect(() => {
    console.log("reset values / student ", selectedStudent);
    form.setFieldsValue({
      name: selectedStudent == null ? "" : selectedStudent.name,
      email: selectedStudent == null ? "" : selectedStudent.email,
      gender: selectedStudent == null ? "" : selectedStudent.gender,
    });
  });

  // when after successfully submited a form, will be invoked.
  const onFinish = (student) => {
    // while sending backend user cannot send request again.
    setSubmitting(true);
    console.log(JSON.stringify(student, null, 2));

    // if this drawer is from "Add new student" button -> send POST request.
    // if this drawer is from "Edit" button -> send PUT request.
    if (isRegister) {
      // send POST request.
      addNewStudent(student)
        .then(() => {
          onClose();
          successNotification(
            "Student successfully added",
            `${student.name} was added to the system`
          );
          fetchStudents(); // refresh students data.
        })
        .catch((err) => {
          console.log(err);
          err.response.json().then((res) => {
            console.log(res);
            errorNotification(
              "There was an issue",
              `${res.message} [${res.status}] [${res.error}]`,
              "bottomLeft"
            );
          });
        })
        .finally(() => {
          // while sending backend user cannot send request again.
          setSubmitting(false);
        });
    } else {
      // send PUT request.
      editStudent(selectedStudent.id, student)
        .then(() => {
          onClose();
          successNotification(
            "Student successfully edited",
            `${student.name} was fixed.`
          );
          fetchStudents(); // refresh students data.
        })
        .catch((err) => {
          console.log(err);
          err.response.json().then((res) => {
            console.log(res);
            errorNotification(
              "There was an issue",
              `${res.message} [${res.status}] [${res.error}]`,
              "bottomLeft"
            );
          });
        })
        .finally(() => {
          // while sending backend user cannot send request again.
          setSubmitting(false);
        });
    }
  };

  // when after failed to submit a form, will be invoked.
  const onFinishFailed = (errorInfo) => {
    alert(JSON.stringify(errorInfo, null, 2));
  };

  return (
    <Drawer
      title="Create new student"
      width={720}
      onClose={onClose}
      visible={showDrawer}
      bodyStyle={{ paddingBottom: 80 }}
      footer={
        <div
          style={{
            textAlign: "right",
          }}
        >
          <Button onClick={onClose} style={{ marginRight: 8 }}>
            Cancel
          </Button>
        </div>
      }
    >
      <Form
        layout="vertical"
        onFinishFailed={onFinishFailed}
        onFinish={onFinish}
        form={form}
        hideRequiredMark
      >
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="name"
              label="Name"
              rules={[{ required: true, message: "Please enter student name" }]}
            >
              <Input placeholder="Please enter student name" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="email"
              label="Email"
              rules={[
                { required: true, message: "Please enter student email" },
              ]}
            >
              <Input placeholder="Please enter student email" />
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="gender"
              label="Gender"
              rules={[{ required: true, message: "Please select a gender" }]}
            >
              <Select placeholder="Please select a gender">
                <Option value="MALE">MALE</Option>
                <Option value="FEMALE">FEMALE</Option>
                <Option value="PREFER_NOT_TO_SAY">PREFER NOT TO SAY</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col span={12}>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Submit
              </Button>
            </Form.Item>
          </Col>
        </Row>
        <Row>{submitting && <Spin indicator={antIcon} />}</Row>
      </Form>
    </Drawer>
  );
}

export default StudentDrawerForm;
