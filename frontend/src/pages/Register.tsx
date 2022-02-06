import { Register as RegisterComponent } from "../components/Register";
import LogInComponent from "../components/LogIn";
import { Container } from "react-bootstrap";
import { strings } from "../i18n/i18n";
import { Helmet } from "react-helmet";

export default function Register() {
  return (
    <>
      <Helmet>
        <title>{strings.collection.pageTitles.register}</title>
      </Helmet>
      <Container style={{ width: "50%" }} className=" min-height">
        <RegisterComponent />
      </Container>
    </>
  );
}
