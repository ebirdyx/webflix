import React, {useContext} from "react";
import {Container, Nav, Navbar} from "react-bootstrap";
import AuthContext from '../store/auth_context';
import {useNavigate} from "react-router-dom";

const Navigation = () => {
  const navigate = useNavigate();
  const authContext = useContext(AuthContext);

  const handleLogout = () => {
    authContext.logout();
    navigate("/");
  }

  return (
    <Navbar bg="dark" expand="lg" variant="dark" >
      <Container>
        <Navbar.Brand href="#home">Webflix</Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />

        <Navbar.Collapse id="basic-navbar-nav">
          {authContext.isLoggedIn &&
            <Nav className="me-auto">
              <Nav.Link href="/">Home</Nav.Link>
            </Nav>
          }
        </Navbar.Collapse>

        {authContext.isLoggedIn &&
          <Navbar.Collapse className="justify-content-end">
            <Navbar.Text>
              {authContext.user.firstName} {authContext.user.lastName}
            </Navbar.Text>

            <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
          </Navbar.Collapse>
        }
      </Container>
    </Navbar>
  )
}

export default Navigation;
