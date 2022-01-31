import { useState } from "react";
import { Form, Card, FormLabel, Button } from "react-bootstrap";
import { useListCategories } from "../features/api/categories/categoriesSlice";
import { strings } from "../i18n/i18n";
import { Category } from "../features/api/categories/types";

function CreateArticleForm() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [articleCategories, setArticleCategories] = useState([]);
  // const [images, setImages] = useState([]);

  const handleChange = (event: any) => {
    const { checked, value } = event.currentTarget;

    setArticleCategories((prev: any) =>
      checked ? [...prev, value] : prev.filter((val: Category) => val !== value)
    );
  };

  const submitArticle = (e: any) => {
    e.preventDefault();
    const article = { name, description, articleCategories };
    console.log(article); //TODO: Agregar lo de las imagenes y llamar a use createArticle()
  };

  const { data: categories, isSuccess } = useListCategories();

  return (
    <Card className="card-style create-card">
      {isSuccess && categories && (
        <Card.Body className="form-container">
          <Card.Title>
            <h3 className="fw-bold">
              {strings.collection.article.createArticle.title}
            </h3>
            <hr></hr>
          </Card.Title>
          <Form onSubmit={submitArticle}>
            <div className="my-2">
              <Form.Label className="lead">
                {strings.collection.article.createArticle.articleName}
              </Form.Label>
              <Form.Control
                type="text"
                placeholder={
                  strings.collection.article.createArticle.articleNameLabel
                }
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
              ></Form.Control>
            </div>
            <div className="my-2">
              <Form.Label className="lead">
                {strings.collection.article.createArticle.articleDescription}
              </Form.Label>
              <Form.Control
                as="textarea"
                type="text"
                placeholder={
                  strings.collection.article.createArticle
                    .articleDescriptionLabel
                }
                required
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></Form.Control>
            </div>
            <div>
              <Form.Label className="lead">
                {strings.collection.article.createArticle.selectCategory}
              </Form.Label>
              <div className="category-list-container my-2 mx-1">
                {categories.map((cat, i) => (
                  <Form.Check
                    key={i.toString()}
                    id={i.toString()}
                    value={cat.description}
                    type="checkbox"
                    checked={articleCategories.some(
                      (val) => val === cat.description
                    )}
                    onChange={handleChange}
                    // inline
                    label={cat.description}
                  ></Form.Check>
                ))}
              </div>
            </div>
            <div className="d-grid gap-2">
              <Button
                className="bg-color-action btn-dark mt-3 mb-2"
                type="submit"
              >
                {strings.collection.article.createArticle.create}
              </Button>
            </div>
          </Form>
        </Card.Body>
      )}
    </Card>
  );
}

export default CreateArticleForm;
