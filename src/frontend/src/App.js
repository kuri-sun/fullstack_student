import {useEffect, useState} from "react";
import {deleteStudent, getAllStudents} from "./client";
import {
    Avatar,
    Badge,
    Breadcrumb,
    Button,
    Divider,
    Empty,
    Layout,
    Menu,
    Popconfirm,
    Radio,
    Spin,
    Table,
    Tag,
} from "antd";
import {LoadingOutlined, PlusOutlined, TeamOutlined, UserOutlined,} from "@ant-design/icons";
import StudentDrawerForm from "./StudentDrawerForm.js";
import "./App.css";
import {errorNotification, successNotification} from "./Notification";

const { Header, Content, Footer, Sider } = Layout;

const TheAvatar = ({ name }) => {
  let trim = name.trim();
  if (trim.length === 0) {
    return <Avatar icon={<UserOutlined />} />;
  }
  const split = trim.split(" ");
  if (split.length === 1) {
    return <Avatar>{name.charAt(0)}</Avatar>;
  }
  return <Avatar>{`${name.charAt(0)}${name.charAt(name.length - 1)}`}</Avatar>;
};

// send request to delete student.
const removeStudent = (studentId, callback) => {
  deleteStudent(studentId)
    .then(() => {
      successNotification(
        "Student deleted",
        `Student with ${studentId} was deleted`
      );
      callback();
    })
    .catch((err) => {
      err.response.json().then((res) => {
        console.log(res);
        errorNotification(
          "There was an issue",
          `${res.message} [${res.status}] [${res.error}]`
        );
      });
    });
};

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function App() {
  // states
  const [students, setStudents] = useState([]);
  const [collapsed, setCollapsed] = useState(false);
  const [fetching, setFetching] = useState(true);
  const [showDrawer, setShowDrawer] = useState(false);
  const [isRegister, setIsRegister] = useState(true);
  const [selectedStudent, setSelectedStudent] = useState(null);

  // fetch student data from api.
  const fetchStudents = () =>
    getAllStudents()
      .then((res) => res.json())
      .then((data) => {
        setStudents(data);
      })
      .catch((err) => {
        console.log(err.response);
        err.response.json().then((res) => {
          errorNotification(
            "There was an issue",
            `${res.message} [${res.status}] [${res.error}]`
          );
        });
      })
      .finally(() => setFetching(false));

  // call once when user entered this page.
  useEffect(() => {
    console.log("component is mounted");
    fetchStudents();
  }, []);

  // for rendering student data.
  const renderStudents = () => {
    // while fetching
    if (fetching) {
      return <Spin indicator={antIcon} />;
    }
    // if no students yet.
    if (students.length <= 0) {
      return (
        <>
          <Button
            onClick={() => {
              renderStudents();
              setIsRegister(true);
              setSelectedStudent(null);
              setShowDrawer(!showDrawer);
            }}
            type="primary"
            shape="round"
            icon={<PlusOutlined />}
            size="small"
          >
            Add New Student
          </Button>
          <StudentDrawerForm
            isRegister={isRegister}
            showDrawer={showDrawer}
            setShowDrawer={setShowDrawer}
            fetchStudents={fetchStudents}
            student={selectedStudent}
          />
          <Empty />
        </>
      );
    }
    // is students data coming back
    return (
      <>
        <StudentDrawerForm
          isRegister={isRegister}
          showDrawer={showDrawer}
          setShowDrawer={setShowDrawer}
          fetchStudents={fetchStudents}
          selectedStudent={selectedStudent}
        />
        <Table
          dataSource={students}
          columns={columns(fetchStudents)}
          bordered
          title={() => (
            <>
              <Tag>All students count</Tag>
              <Badge count={students.length} className="site-badge-count-4" />
              <br />
              <br />
              <Button
                onClick={() => {
                  renderStudents();
                  setIsRegister(true);
                  setSelectedStudent(null);
                  setShowDrawer(!showDrawer);
                }}
                type="primary"
                shape="round"
                icon={<PlusOutlined />}
                size="small"
              >
                Add New Student
              </Button>
            </>
          )}
          pagination={{ pageSize: 50 }}
          scroll={{ y: 500 }}
          rowKey={(student) => student.id}
        />
      </>
    );
  };

  // return how to render the table.
  const columns = (fetchStudents) => [
    {
      title: "",
      dataIndex: "avatar",
      key: "avatar",
      render: (text, student) => <TheAvatar name={student.name} />,
    },
    {
      title: "Id",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Gender",
      dataIndex: "gender",
      key: "gender",
    },
    {
      title: "Actions",
      key: "actions",
      render: (text, student) => (
        <Radio.Group key={student.id}>
          <Popconfirm
            placement="topRight"
            title={`Are you sure to delete ${student.name}`}
            onConfirm={() => removeStudent(student.id, fetchStudents)}
            okText="Yes"
            cancelText="No"
          >
            <Radio.Button value="small">Delete</Radio.Button>
          </Popconfirm>
          <Radio.Button
            key={student.id}
            onClick={() => {
              renderStudents();
              setIsRegister(false);
              setSelectedStudent(student);
              setShowDrawer(!showDrawer);
            }}
            value="small"
          >
            Edit
          </Radio.Button>
        </Radio.Group>
      ),
    },
  ];

  // return UI
  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
        <div className="logo" />
        <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline">
          <Menu.Item key="1" icon={<TeamOutlined />}>
            Board
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout className="site-layout">
        <Header className="site-layout-background" style={{ padding: 0 }} />
        <Content style={{ margin: "0 16px" }}>
          <Breadcrumb style={{ margin: "16px 0" }}></Breadcrumb>
          <div
            className="site-layout-background"
            style={{ padding: 24, minHeight: 360 }}
          >
            {renderStudents()}
          </div>
        </Content>
        <Footer style={{ textAlign: "center" }}>
          <Divider>Student FullStack</Divider>
        </Footer>
      </Layout>
    </Layout>
  );
}

export default App;
