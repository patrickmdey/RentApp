import {
  Button,
  Card,
  Col,
  Container,
  Form,
  FormControl,
  InputGroup,
  Row,
} from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { useCreateArticle } from "../features/api/articles/articlesSlice";
import { strings } from "../i18n/i18n";
import { useForm, Controller, Path } from "react-hook-form";

interface CreateArticleForm {
  name: string;
  description: string;
  pricePerDay: string;
  //categories: Category[];
  //TODO: images: multipart ?
}

export default function CreateArticleForm() {
  const [create, result] = useCreateArticle();

  function onSubmit(data: CreateArticleForm) {
    console.log("submit");
  }

  const { control, handleSubmit } = useForm<CreateArticleForm>({
    defaultValues: {
      name: "",
      description: "",
      pricePerDay: "",
    },
  });

  function TextBox(
    label: string,
    name: Path<CreateArticleForm>,
    placeholder: string,
    type: string,
    prependIcon: any = null,
    appendIcon: any = null
  ) {
    return (
      <Controller
        name={name}
        control={control}
        render={({ field }) => (
          <Form.Group className="mt-3 ">
            <Form.Label className="font-weight-bold">
              {" "}
              <b>{label}</b>{" "}
            </Form.Label>
            <InputGroup>
              {prependIcon != null && (
                <InputGroup.Text>{prependIcon}</InputGroup.Text>
              )}
              <FormControl placeholder={placeholder} type={type} {...field} />
              {appendIcon != null && (
                <InputGroup.Text>{appendIcon}</InputGroup.Text>
              )}
            </InputGroup>
          </Form.Group>
        )}
      />
    );
  }

  return (
    <>
      <Card className="shadow card-style create-card mx-3">
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Container>
            <h3 className="fw-bold my-1">
              {strings.collection.article.createArticle.title}
            </h3>
            <Row>
              {TextBox(
                strings.collection.article.createArticle.articleName,
                "name",
                strings.collection.article.createArticle.articleNameLabel,
                "text"
              )}
            </Row>
            <Row>
              {TextBox(
                strings.collection.article.createArticle.articleDescription,
                "description",
                strings.collection.article.createArticle
                  .articleDescriptionLabel,
                "text"
              )}
            </Row>
            <Row>
              {TextBox(
                strings.collection.article.createArticle.pricePerDay,
                "pricePerDay",
                "0",
                "number",
                <span>$</span>
              )}
            </Row>

            <Row className="justify-content-center">
              <Button className=" bg-color-action btn-dark mt-3 mb-2">
                {strings.collection.article.createArticle.create}
              </Button>
            </Row>
          </Container>
        </Form>
      </Card>
    </>
  );
}
