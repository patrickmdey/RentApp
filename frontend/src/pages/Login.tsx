import LogInComponent from "../components/LogIn";
import { Container } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { strings } from "../i18n/i18n";

export default function Login() {
  return (
    <>
      <Helmet>
        <title>{strings.collection.pageTitles.login}</title>
      </Helmet>
      <Container style={{ width: "50%" }} className=" min-height">
        <LogInComponent />
      </Container>
    </>
  );
}
