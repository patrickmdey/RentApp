import { Button, Col, Form, Modal, Row } from "react-bootstrap";
import { useForm } from "react-hook-form";
import { useCreateRentProposal } from "../../features/api/rentProposals/rentProposalsSlice";
import { CreateRentProposalParameters } from "../../features/api/rentProposals/types";
import { strings } from "../../i18n/i18n";
import FormInput from "../Forms/FormInput";

interface RequestFormT {
  message: string;
  startDate: Date;
  endDate: Date;
  articleId: number;
  renterId: number;
}

function RequestForm() {
  const { register, handleSubmit } = useForm<RequestFormT>({
    defaultValues: {
      message: "",
      startDate: undefined,
      endDate: undefined,
      articleId: undefined,
      renterId: undefined,
    },
  });

  const [createRequest] = useCreateRentProposal();

  function onSubmit(data: RequestFormT) {
    // console.log(data);
    createRequest({
      ...data,
      startDate: data.startDate.toString(),
      endDate: data.endDate.toString(),
    });
  }

  return (
    <div className="modal-content bg-color-grey">
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body className="bg-color-grey">
          <Row>
            <Col md={6} lg={6} className="my-2">
              <FormInput
                register={register}
                label={strings.collection.article.requestArticle.startDate}
                name="startDate"
                type="date"
              />
            </Col>
            <Col md={6} lg={6} className="my-2">
              <FormInput
                register={register}
                label={strings.collection.article.requestArticle.endDate}
                name="endDate"
                type="date"
              />
            </Col>
          </Row>
          <FormInput
            register={register}
            label={strings.collection.article.requestArticle.message}
            placeholder={
              strings.collection.article.requestArticle.messagePlaceHolder
            }
            name="message"
            type="text"
          />
        </Modal.Body>
        <Modal.Footer>
          <Button type="submit" className="bg-color-action color-grey">
            {strings.collection.article.requestArticle.send}
          </Button>
        </Modal.Footer>
      </Form>
    </div>
  );
}

export default RequestForm;
