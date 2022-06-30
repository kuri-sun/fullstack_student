import fetch from "unfetch";

// PROD (temporary heroku deployment)
const baseUrl = "https://student-fullstack-backend.herokuapp.com";
// DEV
//const baseUrl = "http://localhost:8080";

// check http response from backend.
const checkStatus = (response) => {
  if (response.ok) {
    return response;
  }
  // convert non-2xx HTTP responses into errors:
  const error = new Error(response.statusText);
  error.response = response;
  return Promise.reject(error);
};

// GET all students data from backend.
export const getAllStudents = () =>
  fetch(`${baseUrl}/api/v1/students`).then(checkStatus);

// POST register new student data to backend.
export const addNewStudent = (student) =>
  fetch(`${baseUrl}/api/v1/students`, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify(student),
  }).then(checkStatus);

// POST register new student data to backend.
export const editStudent = (studentId, student) =>
  fetch(`${baseUrl}/api/v1/students/${studentId}`, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "PUT",
    body: JSON.stringify(student),
  }).then(checkStatus);

// DELETE delete a student data.
export const deleteStudent = (studentId) =>
  fetch(`${baseUrl}/api/v1/students/${studentId}`, {
    method: "DELETE",
  }).then(checkStatus);
