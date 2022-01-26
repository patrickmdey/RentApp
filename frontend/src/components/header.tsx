import { useAppSelector } from "../hooks";
import { Container, Image, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import React from "react";
import {
  BsBoxArrowInLeft,
  BsFillInboxFill,
  BsPersonFill,
} from "react-icons/bs";
import { Link } from "react-router-dom";
import { strings } from "../i18n/i18n";

const logo = require("../images/rentapp-logo.png");

function RenderLoggedInNavBar() {
  const name = useAppSelector((state) => "Lucas");
  return (
    <NavDropdown
      title={name}
      className="active color-grey h6"
      id="collasible-nav-dropdown"
    >
      <NavDropdown.Item>
        <Link to="/">
          <BsFillInboxFill />
          {strings.collection.header.requests}
        </Link>
      </NavDropdown.Item>

      <NavDropdown.Item>
        <Link to="/saDASD">
          <BsPersonFill />
          {strings.collection.header.profile}
        </Link>
      </NavDropdown.Item>

      <NavDropdown.Item>
        <Link to="/ASDASDSAD">
          <BsBoxArrowInLeft />
          {strings.collection.header.logout}
        </Link>
      </NavDropdown.Item>
    </NavDropdown>
  );
}

function RenderLoggedOutNavBar() {
  return (
    <React.Fragment>
      <LinkContainer to="/users">
        <Nav.Link className="active h6">
          {strings.collection.header.login}
        </Nav.Link>
      </LinkContainer>

      <LinkContainer to="/users">
        <Nav.Link className="active h6">
          {strings.collection.header.signup}
        </Nav.Link>
      </LinkContainer>
    </React.Fragment>
  );
}

function Header() {
  const isAuthenticated = useAppSelector((state) => state.auth.token != null);

  return (
    <Navbar
      collapseOnSelect
      expand="lg"
      bg="primary"
      className="mb-2"
      variant="dark"
    >
      <Container>
        <LinkContainer to="/">
          <Navbar.Brand>
            <Image alt="rentapp" src={logo} height="50px" />
          </Navbar.Brand>
        </LinkContainer>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse className="mt-2" id="responsive-nav-bar">
          <Nav className="ms-auto">
            <LinkContainer to="/home">
              <Nav.Link className="active h6">
                {strings.collection.header.marketplace}
              </Nav.Link>
            </LinkContainer>

            <LinkContainer to="/about">
              <Nav.Link className="active h6">
                {strings.collection.header.publishArticle}
              </Nav.Link>
            </LinkContainer>

            {isAuthenticated ? RenderLoggedInNavBar() : RenderLoggedOutNavBar()}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;
