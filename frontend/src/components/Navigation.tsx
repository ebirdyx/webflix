import React, {useContext, useEffect} from "react";
import {Container, Nav, Navbar} from "react-bootstrap";
import AuthContext from '../store/auth_context';

const Navigation = () => {
  const authContext = useContext(AuthContext);

  return (
    <Navbar bg="dark" expand="lg" variant="dark" >
      <Container>
        <Navbar.Brand href="#home">Webflix</Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {authContext.isLoggedIn &&
              <Nav.Link href="#home">Home</Nav.Link>
            }

            {authContext.isLoggedIn &&
              <Nav.Link href="#link">Link</Nav.Link>
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  )
}

export default Navigation;
