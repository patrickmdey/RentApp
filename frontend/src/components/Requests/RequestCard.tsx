import {RentProposal} from "../../features/api/rentProposals/types";
import {Button, Card, Image, Row} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {strings} from "../../i18n/i18n";


export default function RequestCard(props: RentProposal){
    // TODO: Call the Articles endpoint to get this
    const articleName:string = "iPhone";
    const articlePhoto: string = "http://pawserver.it.itba.edu.ar/paw-2021b-3/image/66";

    // TODO: Call the User's endpoint to get this
    const renterFirstName: string = "Lucas";
    const renterLastName: string = "Dell'Isola";
    return (
        <Card className="card-style my-requests-card my-2">
            {/*// TODO: Duda, esto va a funcionar con el routing que tenemos?*/}
            <LinkContainer to={props.articleUrl.toString()}>
                <h3 className="color-action mb-0"> {articleName} </h3>
            </LinkContainer>
            <hr/>
            <Row>
                <div className="col-3">
                   <Image src={articlePhoto} width="200px" alt={articleName}/>
                </div>
                <div className="col-9">
                    <p className="lead mb-2">
                        {strings.formatString(strings.collection.requestCard.name,renterFirstName,renterLastName)}
                    </p>
                    <Row className="justify-content-start">
                        <p className="lead col-lg-6 col-md-6 col-12">
                            {strings.formatString(strings.collection.requestCard.startDate,props.startDate)}
                        </p>
                        <p className="lead col-lg-6 col-md-6 col-12">
                            {strings.formatString(strings.collection.requestCard.endDate,props.endDate)}
                        </p>
                    </Row>
                    <h4> {strings.collection.requestCard.messageTitle}</h4>
                    <p> {props.message} </p>
                </div>
            </Row>
            {
                props.state == 0 &&

                <div className="d-flex justify-content-end my-2">
                    <Button className="bg-color-action color-grey me-1">
                        {strings.collection.requestCard.acceptButton}
                    </Button>
                    <button  className="btn btn-link color-danger">
                        {strings.collection.requestCard.rejectButton}
                    </button>
                </div>
            }
        </Card>
    )
}