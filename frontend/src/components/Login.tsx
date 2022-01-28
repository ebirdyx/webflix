import React, {FormEvent, useContext, useState} from "react";
import {Form, Button, Container} from 'react-bootstrap';
import UserApi from "../api/UserApi";
import AuthContext from '../store/auth_context';
import {useNavigate} from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const authContext = useContext(AuthContext);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [wrongCredentials, setWrongCredentials] = useState(false);

  const submitHandler = (e: FormEvent) => {
    e.preventDefault();

    if (username.length !== 0 && password.length !== 0)
      UserApi()
        .login(username, password)
        .then(res => {
          authContext.login(res.data);
          navigate("/")
        })
        .catch(err => setWrongCredentials(true))
  }

  return (
    <Container>
      <div className="p-lg-5">
        <Form onSubmit={submitHandler}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter email"
              className=""
              onChange={(e => setUsername(e.target.value))}
            />
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Password"
              className=""
              onChange={(e => setPassword(e.target.value))}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="errorMessage">
            {wrongCredentials &&
              <p className="text-danger">Wrong credentials!</p>
            }
          </Form.Group>
          <Button variant="primary" type="submit">
            Login
          </Button>
        </Form>
      </div>
    </Container>
  )
}

export default Login;