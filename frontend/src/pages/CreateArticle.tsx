import CreateArticleForm from "../components/CreateArticleFrom";
import { Container } from "react-bootstrap";
export default function CreateArticle() {
  return (
    <Container style={{ width: "50%" }} className=" min-height">
      <CreateArticleForm></CreateArticleForm>
    </Container>
  );
}
